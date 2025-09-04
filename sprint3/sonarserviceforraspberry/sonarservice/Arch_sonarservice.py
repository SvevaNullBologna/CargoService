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
with Diagram('sonarserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctx_sonarservice', graph_attr=nodeattr):
          sonar_listener=Custom('sonar_listener','./qakicons/symActorWithobjSmall.png')
          sonar_reactor=Custom('sonar_reactor','./qakicons/symActorWithobjSmall.png')
          led_device=Custom('led_device','./qakicons/symActorWithobjSmall.png')
     sonar_listener >> Edge( label='distance', **eventedgeattr, decorate='true', fontcolor='red') >> sonar_reactor
     sonar_reactor >> Edge( label='anomalyDetected', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sonar_reactor >> Edge( label='anomalyFixed', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sonar_reactor >> Edge( label='productDetected', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     sys >> Edge( label='anomalyDetected', **evattr, decorate='true', fontcolor='darkgreen') >> led_device
     sys >> Edge( label='anomalyFixed', **evattr, decorate='true', fontcolor='darkgreen') >> led_device
     sys >> Edge( label='productDetected', **evattr, decorate='true', fontcolor='darkgreen') >> led_device
diag
