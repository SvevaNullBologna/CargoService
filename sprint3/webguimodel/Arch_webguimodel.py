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
          webguimodel=Custom('webguimodel','./qakicons/symActorWithobjSmall.png')
          gui_request_handler=Custom('gui_request_handler','./qakicons/symActorWithobjSmall.png')
          gui_api_gateway=Custom('gui_api_gateway','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctx_cargoservice', graph_attr=nodeattr):
          hold=Custom('hold(ext)','./qakicons/externalQActor.png')
     with Cluster('ctx_springboot_webgui', graph_attr=nodeattr):
          webgui=Custom('webgui(ext)','./qakicons/externalQActor.png')
     hold >> Edge(color='blue', style='solid',  decorate='true', label='<update &nbsp; >',  fontcolor='blue') >> webguimodel
diag
