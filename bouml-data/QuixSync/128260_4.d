format 220

activitycanvas 128004 activity_ref 128516 // Vergleich
  
  xyzwh 12 12 2000 685 923
end
activityactioncanvas 128132 activityaction_ref 128516 // activity action Aufruf der Schleife von Elementen (Ordner/Dateien) der Quelldatei
  
  show_opaque_action_definition default
  xyzwh 215 58 2005 284 53
end
activitynodecanvas 128260 activitynode_ref 128260 // initial_node
  xyz 346 26 2005
end
activityactioncanvas 128516 activityaction_ref 128644 // activity action Suche der Datei mit dem Erstelldatum im Zieldatei
  
  show_opaque_action_definition default
  xyzwh 261 140 2005 194 53
end
activitynodecanvas 128772 activitynode_ref 128388 // decision
  xyz 343 224 2005
end
textcanvas 129028 "[Gefunden?]"
  xyzwh 292 194 2011 59 28
activityactioncanvas 129156 activityaction_ref 128772 // activity action Setzen von isCompared = true
  
  show_opaque_action_definition default
  xyzwh 182 262 2005 130 53
end
activityactioncanvas 129284 activityaction_ref 128900 // activity action Speicherung des Elementes in ReturnCopyArray
  
  show_opaque_action_definition default
  xyzwh 373 346 2005 170 53
end
textcanvas 129668 "[Ja]"
  xyzwh 325 226 2011 26 27
textcanvas 129796 "[Nein]"
  xyzwh 368 226 2011 26 28
activitynodecanvas 129924 activitynode_ref 128516 // decision
  xyz 231 355 2005
end
textcanvas 130180 "[Name, Dateigröße oder \"zuletzt
geändert\" verschieden?]"
  xyzwh 87 325 2010 167 45
textcanvas 130692 "[Ja]"
  xyzwh 252 357 2011 25 20
activityactioncanvas 130820 activityaction_ref 129028 // activity action Datei ignoriert
  
  show_opaque_action_definition default
  xyzwh 198 416 2005 99 53
end
textcanvas 131076 "[Nein]"
  xyzwh 243 389 2010 26 21
activitynodecanvas 131460 activitynode_ref 128644 // decision
  xyz 150 576 2005
end
textcanvas 131716 "[isHardSync?]"
  xyzwh 74 577 2010 67 28
activityactioncanvas 131844 activityaction_ref 129284 // activity action Aufruf der Schleife von Elementen der ersten Ebene von dem Zielindex
  
  show_opaque_action_definition default
  xyzwh 367 564 2005 207 53
end
textcanvas 132100 "[Ja]"
  xyzwh 175 577 2011 25 28
textcanvas 132484 "[Nein]"
  xyzwh 165 611 2011 40 26
activitynodecanvas 132612 activitynode_ref 128772 // decision
  xyz 514 657 2005
end
textcanvas 132868 "[isCompared == true?]"
  xyzwh 439 621 2011 85 34
activitynodecanvas 132994 activitynode_ref 128258 // merge
  xyz 150 635 2011
end
activityactioncanvas 132996 activityaction_ref 129540 // activity action Speicherung des Elementes in ReturnDeleteArray
  
  show_opaque_action_definition default
  xyzwh 488 726 2005 167 53
end
textcanvas 133252 "[Nein]"
  xyzwh 485 660 2011 32 28
textcanvas 133508 "[Ja]"
  xyzwh 529 694 2011 27 19
activitynodecanvas 134146 activitynode_ref 128386 // merge
  xyz 442 735 2005
end
activityactioncanvas 134148 activityaction_ref 129668 // activity action Speicherung von ReturnCopyArray und ReturnDeleteArray in einer Datei
  
  show_opaque_action_definition default
  xyzwh 176 827 2005 232 53
end
activitynodecanvas 134404 activitynode_ref 129028 // activity_final
  xyz 277 900 2005
end
textcanvas 134916 "[Quellindex-Schleife 
vollständig durchlaufen?]"
  xyzwh 129 505 2005 136 38
activitynodecanvas 136068 activitynode_ref 135812 // merge
  xyz 447 427 2010
end
activitynodecanvas 136836 activitynode_ref 135940 // decision
  xyz 63 501 2010
end
activityactioncanvas 137220 activityaction_ref 136196 // activity action Aufruf des nächsten Elements
  
  show_opaque_action_definition default
  xyzwh 27 415 2005 100 60
end
textcanvas 137860 "[Ja]"
  xyzwh 76 539 2016 20 23
textcanvas 137988 "[Nein]"
  xyzwh 77 489 2005 36 20
activitynodecanvas 138116 activitynode_ref 136068 // decision
  xyz 151 709 2005
end
textcanvas 138372 "[ReturnCopyArray und
ReturnDeleteArray leer?]"
  xyzwh 35 671 2016 123 40
textcanvas 138628 "[Nein]"
  xyzwh 176 712 2010 43 28
textcanvas 139012 "[Ja]"
  xyzwh 142 743 2011 36 21
textcanvas 140036 "Zielindex-Schleife 
vollständig 
durchlaufen?"
  xyzwh 378 726 2005 64 56
activitynodecanvas 140164 activitynode_ref 136324 // decision
  xyz 326 738 2005
end
textcanvas 140676 "[Ja]"
  xyzwh 340 721 2010 22 19
textcanvas 140932 "[Nein]"
  xyzwh 339 776 2011 39 21
activityactioncanvas 141700 activityaction_ref 136452 // activity action Aufruf des nächsten Elements
  
  show_opaque_action_definition default
  xyzwh 551 788 2005 100 60
end
flowcanvas 128388 flow_ref 128516 // <flow>
  
  from ref 128260 z 2006 to ref 128132
   write_horizontally default
end
flowcanvas 128514 flow_ref 128130 // <flow>
  
  from ref 129284 z 2011 to ref 136068
   write_horizontally default
end
flowcanvas 128644 flow_ref 128644 // <flow>
  
  from ref 128132 z 2006 to ref 128516
   write_horizontally default
end
flowcanvas 128900 flow_ref 128772 // <flow>
  
  from ref 128516 z 2006 to ref 128772
   write_horizontally default
end
flowcanvas 129412 flow_ref 128900 // <flow>
  decenter_begin 540
  
  from ref 128772 z 2006 to point 244 241
  line 139396 z 2006 to ref 129156
   write_horizontally default
end
flowcanvas 129540 flow_ref 129028 // <flow>
  decenter_end 493
  
  from ref 128772 z 2006 to point 453 239
  line 139268 z 2006 to ref 129284
   write_horizontally default
end
flowcanvas 130050 flow_ref 128898 // <flow>
  decenter_end 513
  
  from ref 136836 z 2011 to point 71 592
  line 130178 z 2011 to ref 131460
   write_horizontally default
end
flowcanvas 130052 flow_ref 129156 // <flow>
  
  from ref 129156 z 2006 to ref 129924
   write_horizontally default
end
flowcanvas 130564 flow_ref 129284 // <flow>
  decenter_begin 458
  
  from ref 129924 z 2006 to ref 129284
   write_horizontally default
end
flowcanvas 130948 flow_ref 129412 // <flow>
  
  from ref 129924 z 2006 to ref 130820
   write_horizontally default
end
flowcanvas 131972 flow_ref 129796 // <flow>
  
  from ref 131460 z 2006 to ref 131844
   write_horizontally default
end
flowcanvas 132740 flow_ref 130052 // <flow>
  decenter_begin 763
  
  from ref 131844 z 2006 to ref 132612
   write_horizontally default
end
flowcanvas 133122 flow_ref 129410 // <flow>
  decenter_begin 479
  
  from ref 140164 z 2012 to point 336 648
  line 142980 z 2012 to ref 132994
   write_horizontally default
end
flowcanvas 133250 flow_ref 129538 // <flow>
  
  from ref 131460 z 2012 to ref 132994
   write_horizontally default
end
flowcanvas 133378 flow_ref 129666 // <flow>
  
  from ref 132994 z 2012 to ref 138116
   write_horizontally default
end
flowcanvas 133380 flow_ref 130308 // <flow>
  decenter_begin 518
  decenter_end 238
  
  from ref 132612 z 2006 to ref 132996
   write_horizontally default
end
flowcanvas 134274 flow_ref 129922 // <flow>
  decenter_end 518
  
  from ref 132612 z 2006 to point 453 675
  line 142852 z 2006 to ref 134146
   write_horizontally default
end
flowcanvas 134402 flow_ref 130050 // <flow>
  decenter_end 486
  
  from ref 132996 z 2006 to ref 134146
   write_horizontally default
end
flowcanvas 134530 flow_ref 130178 // <flow>
  
  from ref 134146 z 2006 to ref 140164
   write_horizontally default
end
flowcanvas 134532 flow_ref 130948 // <flow>
  
  from ref 134148 z 2006 to ref 134404
   write_horizontally default
end
flowcanvas 134658 flow_ref 130306 // <flow>
  
  from ref 138116 z 2006 to point 157 911
  line 134786 z 2006 to ref 134404
   write_horizontally default
end
flowcanvas 136708 flow_ref 138372 // <flow>
  
  from ref 130820 z 2011 to ref 136068
   write_horizontally default
end
flowcanvas 136964 flow_ref 138500 // <flow>
  decenter_begin 540
  decenter_end 513
  
  from ref 136068 z 2011 to point 459 515
  line 133890 z 2011 to ref 136836
   write_horizontally default
end
flowcanvas 137348 flow_ref 138628 // <flow>
  
  from ref 136836 z 2011 to ref 137220
   write_horizontally default
end
flowcanvas 137476 flow_ref 138756 // <flow>
  
  from ref 137220 z 2006 to point 78 167
  line 137604 z 2006 to ref 128516
   write_horizontally default
end
flowcanvas 138500 flow_ref 139012 // <flow>
  decenter_begin 513
  
  from ref 138116 z 2006 to point 288 724
  line 134914 z 2006 to ref 134148
   write_horizontally default
end
flowcanvas 141828 flow_ref 140164 // <flow>
  decenter_begin 518
  decenter_end 483
  
  from ref 140164 z 2006 to point 336 816
  line 141956 z 2006 to ref 141700
   write_horizontally default
end
flowcanvas 142212 flow_ref 140292 // <flow>
  decenter_begin 583
  decenter_end 490
  
  from ref 141700 z 2006 to point 670 819
  line 143748 z 2006 to point 668 586
  line 142340 z 2006 to ref 131844
   write_horizontally default
end
end
