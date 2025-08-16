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
     with Cluster('ctx_sonarservice', graph_attr=nodeattr):
          listener=Custom('listener','./qakicons/symActorWithobjSmall.png')
          reactor=Custom('reactor','./qakicons/symActorWithobjSmall.png')
     sys >> Edge( label='sonardata', **evattr, decorate='true', fontcolor='darkgreen') >> reactor
     reactor >> Edge( label='anomalyDetected', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     reactor >> Edge( label='anomalyFixed', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     reactor >> Edge( label='productDetected', **eventedgeattr, decorate='true', fontcolor='red') >> sys
diag
