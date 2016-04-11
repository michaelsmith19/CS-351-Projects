  CS351 Project 2 - Implementing FrobWorld

  CONTACT INFORMATION
     Project author:  Michael Smith
     Email:           msmith19@unm.edu
     Date:            Sun Oct 27, 2013

  PROJECT DESCRIPTION

     FrobWorld is a discrete event simulation in which an herbivore with
     certain specifications attempts to live in a simulated world.  
     See http://cs.unm.edu/~ackley/351/projects/2 for a
     complete description.  
     
  SPECIFICATION ISSUES

    The following discussion is with respect to the FrobWorld v1.0
    specification.  The following issues were noted during project
    development, presented along with the chosen resolutions:
    

  IMPLEMENTATION SPECIFICS

    The enclosed FrobWorld is a fully-conforming version of the 
    FrobWorld v1 specification

    Points of particular design interest include
    	-The GUI runs in terms of days. It will have many movements happening
    	all at once especially for the grass. This is due to having a low 
    	initial update period. They get a better spread later in a run after
    	they have failed some splits and had their update period doubled.
    	
    ACKNOWLEDGMENTS:

     Professor Ackley designed the spec as well as providing some test
     cases. Trent Small for some help on debugging why it was not being 
     repeatable with the same seed.  

  KNOWN BUGS:
  
  	 I don't really think it is a bug but I was expecting more populations
  	 to survive. However the consistency of the metabolisms of the ones that
  	 did pass makes me think it is fine. 

     