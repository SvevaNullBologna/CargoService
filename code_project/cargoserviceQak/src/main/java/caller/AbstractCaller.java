package main.java.caller;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.Connection;
import unibo.basicomm23.utils.ConnectionFactory;

public abstract class AbstractCaller {
    protected String name;
    protected Interaction commChannel;
    protected ProtocolType protocol;
    protected String hostAddr;
    protected String entry;
    protected boolean connected = false;

    public AbstractCaller(String name, ProtocolType protocol, String hostAddr, String entry){
        this.name     = name;
        this.protocol = protocol;
        this.hostAddr = hostAddr;
        this. entry   = entry;
 
        CommUtils.outblue(name + " | CREATED "  );
        //connect();
    }


    protected void connect(){
        CommUtils.outblue(name + " | entry connect commChannel=" + commChannel);
       if( connected ) return;
        connected   = true;
        commChannel = ConnectionFactory.createClientSupport23(protocol, hostAddr, entry);
        ((Connection)commChannel).trace = true;
        CommUtils.outblue(name + " | exit connect client=" + commChannel);
    }

    public void activate(){
        new Thread(){
            public void run(){
                try {
                    connect();
                    body();
                  } catch (Exception e) {
                    CommUtils.outred("");
                }
            }
        }.start();
    }

    protected abstract void body() throws Exception;

    
	protected void createAProduct(String p) throws Exception {
		IApplMessage req = CommUtils.buildRequest(name, "createProduct", p, "productservice");
		CommUtils.outcyan(name + " | sends  " + req ); //+ " on " + commChannel
		try {
			IApplMessage answer = commChannel.request( req ); // raise exception
			CommUtils.outcyan(name + " | createAProduct answer=" + answer.toString() );
		} catch (Exception e) {
			CommUtils.outred(name + " | createAProduct ERROR " + e.getMessage());
		}
		//IApplMessage answer = commChannel.request(req); // raise exception
		//CommUtils.outcyan(name + " | createAProduct answer=" + answer);
	}
	protected boolean getProduct(int n) throws Exception {
        IApplMessage req = CommUtils.buildRequest(name, "getProduct", "product(" + n + ")", "productservice");
        //CommUtils.outblue(name + " | sends request on " + connSupport);
        IApplMessage answer = commChannel.request(req);  //raise exception
//        String outs         = answer.msgContent();
        CommUtils.outblue(name + " | getProduct answer=" + answer);
        return ! answer.toString().contains("error");
		
	}
	
	protected String getAllProducts() throws Exception {
		IApplMessage req = CommUtils.buildRequest(name, "getAllProducts", "dummy(0)", "productservice");
		CommUtils.outcyan(name + " | sends  " + req ); //+ " on " + commChannel
		try {
			IApplMessage answer = commChannel.request( req ); // raise exception
			CommUtils.outcyan(name + " | getAllProducts answer=" + answer.toString() );
			return answer.msgContent();
		} catch (Exception e) {
			CommUtils.outred(name + " | getAllProducts ERROR " + e.getMessage());
			return "";
		}
		
	}
	
}
