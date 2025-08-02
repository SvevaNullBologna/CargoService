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
with Diagram('cargoserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctx_cargoservice', graph_attr=nodeattr):
          companysimulator=Custom('companysimulator','./qakicons/symActorWithobjSmall.png')
          cargoservice=Custom('cargoservice','./qakicons/symActorWithobjSmall.png')
          cargorobot=Custom('cargorobot','./qakicons/symActorWithobjSmall.png')
          sonarmock=Custom('sonarmock','./qakicons/symActorWithobjSmall.png')
          webguimock=Custom('webguimock','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctx_productservice', graph_attr=nodeattr):
          productservice=Custom('productservice(ext)','./qakicons/externalQActor.png')
     with Cluster('ctx_basicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     sys >> Edge( label='productDetected', **evattr, decorate='true', fontcolor='darkgreen') >> cargoservice
     sys >> Edge( label='finishedtransport', **evattr, decorate='true', fontcolor='darkgreen') >> cargoservice
     cargorobot >> Edge( label='finishedtransport', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sys >> Edge( label='anomalyFixed', **evattr, decorate='true', fontcolor='darkgreen') >> cargorobot
     sonarmock >> Edge( label='productDetected', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sonarmock >> Edge( label='anomalyDetected', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     cargorobot >> Edge(color='magenta', style='solid', decorate='true', label='<engage &nbsp; moverobot &nbsp; >',  fontcolor='magenta') >> basicrobot
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<getProduct<font color="darkgreen"> getProductAnswer</font> &nbsp; >',  fontcolor='magenta') >> productservice
     companysimulator >> Edge(color='magenta', style='solid', decorate='true', label='<loadrequest &nbsp; >',  fontcolor='magenta') >> cargoservice
     cargorobot >> Edge(color='blue', style='solid',  decorate='true', label='<setdirection &nbsp; cmd &nbsp; >',  fontcolor='blue') >> basicrobot
     cargoservice >> Edge(color='blue', style='solid',  decorate='true', label='<update &nbsp; >',  fontcolor='blue') >> webguimock
     cargoservice >> Edge(color='blue', style='solid',  decorate='true', label='<command &nbsp; >',  fontcolor='blue') >> cargorobot
diag
