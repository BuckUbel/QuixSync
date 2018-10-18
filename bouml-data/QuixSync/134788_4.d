format 220

activityactioncanvas 128004 activityaction_ref 136580 // activity action Auflistung des Inhaltes
  
  show_opaque_action_definition default
  xyzwh 256 149 2011 145 60
end
activitynodecanvas 128132 activitynode_ref 136452 // initial_node
  xyz 316 107 2017
end
activityactioncanvas 128388 activityaction_ref 136708 // activity action Erstellen von zwei Listen (Ordner und Dateien)
  
  show_opaque_action_definition default
  xyzwh 254 250 2023 148 59
end
activityactioncanvas 128772 activityaction_ref 136836 // activity action Speichern der Metainformationen in die Dateiliste (siehe 3.3.1)
  
  show_opaque_action_definition default
  xyzwh 241 353 2017 180 58
end
activityactioncanvas 128900 activityaction_ref 136964 // activity action Speichern der Metainformationen der Ordnerobjekte in die Ordnerliste (siehe 3.3.1)
  
  show_opaque_action_definition default
  xyzwh 215 455 2011 239 53
end
activityactioncanvas 129156 activityaction_ref 137220 // activity action Speichern der Liste mit weiteren Metainformationen in einer Datei
  
  show_opaque_action_definition default
  xyzwh 239 547 2023 192 64
end
activitynodecanvas 129796 activitynode_ref 136708 // activity_final
  xyz 322 640 2011
end
activitycanvas 130052 activity_ref 135044 // Indizierung
  
  xyzwh 119 38 2006 433 667
end
flowcanvas 128260 flow_ref 140420 // <flow>
  
  from ref 128132 z 2018 to ref 128004
   write_horizontally default
end
flowcanvas 128516 flow_ref 140548 // <flow>
  
  from ref 128004 z 2024 to ref 128388
   write_horizontally default
end
flowcanvas 129284 flow_ref 140676 // <flow>
  
  from ref 128388 z 2024 to ref 128772
   write_horizontally default
end
flowcanvas 129412 flow_ref 140804 // <flow>
  
  from ref 128772 z 2018 to ref 128900
   write_horizontally default
end
flowcanvas 129924 flow_ref 141188 // <flow>
  
  from ref 129156 z 2024 to ref 129796
   write_horizontally default
end
flowcanvas 130308 flow_ref 149124 // <flow>
  
  from ref 128900 z 2024 to ref 129156
   write_horizontally default
end
end
