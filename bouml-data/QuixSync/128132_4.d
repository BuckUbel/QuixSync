format 220

activitynodecanvas 128004 activitynode_ref 128004 // initial_node
  xyz 226 58 2000
end
activityactioncanvas 128516 activityaction_ref 128132 // activity action Indizierung
  
  show_opaque_action_definition default
  xyzwh 184 124 2000 105 63
end
activityactioncanvas 128644 activityaction_ref 128260 // activity action Vergleich
  
  show_opaque_action_definition default
  xyzwh 183 226 2000 108 65
end
activityactioncanvas 128772 activityaction_ref 128388 // activity action Synchronisierung
  
  show_opaque_action_definition default
  xyzwh 181 333 2000 111 65
end
activitynodecanvas 129284 activitynode_ref 128132 // activity_final
  xyz 224 445 2000
end
activitycanvas 129540 activity_ref 128260 // Ablauf
  
  xyzwh 62 32 2006 361 485
end
flowcanvas 128900 flow_ref 128004 // <flow>
  
  from ref 128004 z 2001 to ref 128516
   write_horizontally default
end
flowcanvas 129028 flow_ref 128132 // <flow>
  
  from ref 128516 z 2001 to ref 128644
   write_horizontally default
end
flowcanvas 129156 flow_ref 128260 // <flow>
  
  from ref 128644 z 2001 to ref 128772
   write_horizontally default
end
flowcanvas 129412 flow_ref 128388 // <flow>
  
  from ref 128772 z 2001 to ref 129284
   write_horizontally default
end
end
