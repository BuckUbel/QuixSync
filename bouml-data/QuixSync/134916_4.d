format 220

activityactioncanvas 128004 activityaction_ref 137348 // activity action Durchgehen der Liste der fehlenden Daten (ReturnCopyArray)
  
  show_opaque_action_definition default
  xyzwh 345 161 2046 213 63
end
activitynodecanvas 128132 activitynode_ref 136836 // initial_node
  xyz 440 115 2040
end
activityactioncanvas 128388 activityaction_ref 137476 // activity action Kopieren der Daten
  
  show_opaque_action_definition default
  xyzwh 392 269 2040 127 51
end
activitynodecanvas 128644 activitynode_ref 136964 // decision
  xyz 442 362 2046
end
activityactioncanvas 129284 activityaction_ref 137604 // activity action Durchgehen der Liste der zu löschenden Daten (ReturnDeleteArray)
  
  show_opaque_action_definition default
  xyzwh 337 460 2052 237 63
end
activityactioncanvas 129540 activityaction_ref 137732 // activity action Löschen der Daten
  
  show_opaque_action_definition default
  xyzwh 395 578 2040 125 55
end
activitynodecanvas 129796 activitynode_ref 137092 // decision
  xyz 446 679 2046
end
activitynodecanvas 130052 activitynode_ref 137220 // activity_final
  xyz 447 749 2040
end
textcanvas 130692 "[ReturnCopyArray 
vollständig durchgegangen?]"
  xyzwh 456 324 2006 150 36
textcanvas 130820 "[Ja]"
  xyzwh 457 401 2006 17 14
textcanvas 130948 "[Nein]"
  xyzwh 406 360 2006 27 14
textcanvas 131076 "[Nein]"
  xyzwh 414 680 2006 27 14
textcanvas 131204 "[Ja]"
  xyzwh 465 714 2006 23 15
textcanvas 131332 "[ReturnDeleteArray 
vollständig durchgegangen?]"
  xyzwh 459 637 2006 150 36
textcanvas 132868 "[Index inkrementiert]"
  xyzwh 280 278 2000 105 17
textcanvas 132996 "[Index inkrementiert]"
  xyzwh 289 586 2000 105 17
activitycanvas 133124 activity_ref 135172 // Synchronisierung
  
  xyzwh 223 51 2035 451 755
end
flowcanvas 128260 flow_ref 141316 // <flow>
  
  from ref 128132 z 2047 to ref 128004
   write_horizontally default
end
flowcanvas 128516 flow_ref 141444 // <flow>
  
  from ref 128004 z 2047 to ref 128388
   write_horizontally default
end
flowcanvas 129156 flow_ref 141700 // <flow>
  
  from ref 128388 z 2047 to ref 128644
   write_horizontally default
end
flowcanvas 129412 flow_ref 141828 // <flow>
  
  from ref 128644 z 2053 to ref 129284
   write_horizontally default
end
flowcanvas 129668 flow_ref 141956 // <flow>
  
  from ref 129284 z 2053 to ref 129540
   write_horizontally default
end
flowcanvas 129924 flow_ref 142084 // <flow>
  
  from ref 129540 z 2047 to ref 129796
   write_horizontally default
end
flowcanvas 130180 flow_ref 142212 // <flow>
  
  from ref 129796 z 2047 to ref 130052
   write_horizontally default
end
flowcanvas 131716 flow_ref 148868 // <flow>
  
  from ref 129796 z 2047 to point 308 695
  line 132228 z 2047 to point 308 603
  line 132356 z 2047 to ref 129540
   write_horizontally default
end
flowcanvas 132484 flow_ref 148996 // <flow>
  
  from ref 128644 z 2047 to point 317 376
  line 132612 z 2047 to point 317 293
  line 132740 z 2047 to ref 128388
   write_horizontally default
end
end
