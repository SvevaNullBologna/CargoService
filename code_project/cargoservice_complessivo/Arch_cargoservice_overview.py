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
with Diagram('cargoservice_overviewArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctx_cargoservice', graph_attr=nodeattr):
          cargoservice=Custom('cargoservice','./qakicons/symActorWithobjSmall.png')
          cargorobot=Custom('cargorobot','./qakicons/symActorWithobjSmall.png')
          productservice=Custom('productservice','./qakicons/symActorWithobjSmall.png')
          hold=Custom('hold','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctx_productservice', graph_attr=nodeattr):
          productservice=Custom('productservice(ext)','./qakicons/externalQActor.png')
     with Cluster('ctx_basicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot','./qakicons/symActorWithobjSmall.png')
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctx_companysim', graph_attr=nodeattr):
          companysimulator=Custom('companysimulator','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctx_sonarservice', graph_attr=nodeattr):
          sonarservice=Custom('sonarservice','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctx_webgui', graph_attr=nodeattr):
          webgui=Custom('webgui','./qakicons/symActorWithobjSmall.png')
     cargorobot >> Edge(color='magenta', style='solid', decorate='true', label='<step &nbsp; >',  fontcolor='magenta') >> basicrobot
     cargoservice >> Edge(color='magenta', style='solid', decorate='true', label='<checkloadrequest &nbsp; >',  fontcolor='magenta') >> productservice
     companysimulator >> Edge(color='magenta', style='solid', decorate='true', label='<loadrequest &nbsp; >',  fontcolor='magenta') >> cargoservice
     cargorobot >> Edge(color='blue', style='solid',  decorate='true', label='<command &nbsp; >',  fontcolor='blue') >> basicrobot
     sonarservice >> Edge(color='blue', style='solid',  decorate='true', label='<distance &nbsp; >',  fontcolor='blue') >> cargoservice
     cargorobot >> Edge(color='blue', style='solid',  decorate='true', label='<updategui &nbsp; >',  fontcolor='blue') >> webgui
diag
