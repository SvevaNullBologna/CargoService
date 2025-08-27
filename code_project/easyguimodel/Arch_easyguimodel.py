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
with Diagram('easyguimodelArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctx_webgui', graph_attr=nodeattr):
          webgui=Custom('webgui','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctx_easy', graph_attr=nodeattr):
          example=Custom('example(ext)','./qakicons/externalQActor.png')
     example >> Edge(color='blue', style='solid',  decorate='true', label='<message &nbsp; >',  fontcolor='blue') >> webgui
diag
