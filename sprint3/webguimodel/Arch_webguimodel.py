### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('webguimodelArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctx_webguimodel', graph_attr=nodeattr):
          webgui=Custom('webgui','./qakicons/symActorWithobjSmall.png')
          loadrequestsendpage=Custom('loadrequestsendpage','./qakicons/symActorWithobjSmall.png')
          holdshowpage=Custom('holdshowpage','./qakicons/symActorWithobjSmall.png')
          usersim=Custom('usersim','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctx_cargoservice', graph_attr=nodeattr):
          hold=Custom('hold(ext)','./qakicons/externalQActor.png')
          companyrequestreceiver=Custom('companyrequestreceiver(ext)','./qakicons/externalQActor.png')
     webgui >> Edge( label='filteredupdate', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sys >> Edge( label='filteredupdate', **evattr, decorate='true', fontcolor='darkgreen') >> loadrequestsendpage
     sys >> Edge( label='filteredupdate', **evattr, decorate='true', fontcolor='darkgreen') >> holdshowpage
     loadrequestsendpage >> Edge(color='magenta', style='solid', decorate='true', label='<sendrequest<font color="darkgreen"> sendrequestAnswer</font> &nbsp; >',  fontcolor='magenta') >> companyrequestreceiver
     usersim >> Edge(color='blue', style='solid',  decorate='true', label='<hitsend &nbsp; >',  fontcolor='blue') >> loadrequestsendpage
     hold >> Edge(color='blue', style='solid',  decorate='true', label='<update &nbsp; >',  fontcolor='blue') >> webgui
     hold >> Edge(color='blue', style='solid',  decorate='true', label='<update &nbsp; >',  fontcolor='blue') >> holdshowpage
     hold >> Edge(color='blue', style='solid',  decorate='true', label='<update &nbsp; >',  fontcolor='blue') >> loadrequestsendpage
diag
