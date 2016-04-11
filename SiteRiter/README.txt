  CS351 Project 1 - Implementing SiteRiter

  CONTACT INFORMATION
     Project author:  Michael Smith
     Email:           msmith19@unm.edu
     Date:            Thu Sep 19, 2013

  PROJECT DESCRIPTION

     SiteRiter is a system for generating web sites based on custom
     grammars.  See http://cs.unm.edu/~ackley/351/projects/1 for a
     complete description.  
     
  SPECIFICATION ISSUES

    The following discussion is with respect to the SiteRiter v1.0
    specification.  The following issues were noted during project
    development, presented along with the chosen resolutions:
    	((C.1.1.1)) In this rule it seemed to say that the siteriter
    	must be capable of taking a UTF-16. This rule is strangely 
    	worded and leads to the problems described in known bugs. 
    	I have chosen to not declare the reader as UTF-16 which 
    	allows the program to use all the standard English UTF-8 or
    	western encodings (ASCII too).

    

  IMPLEMENTATION SPECIFICS

    The enclosed SiteRiter is a fully-conforming implementation of the
    SiteRiter v1.0 specification.

    Points of particular design interest include
    	-I attempted to simplify the problem by breaking up Tokenizing, 
    	Recognizing, and Loading.  It made it so I have 3 classes that 
    	are relatively small and easier to understand.  However, it is 
    	horribly inefficient since it parses the entire input many times.
    	-I was unable to figure out how to use an Enum to store the token
    	type.  I instead used a class that had Strings for both type and 
    	name.  This was not the best work around due to many calls to the 
    	method .equals which makes the code easy to break.
    	-The extra credits have been attempted. The robots.txt is 
    	working as well as the option to change the port for the server. 
    
    ACKNOWLEDGMENTS:

     Professor Ackley designed the spec as well as providing some test
     cases and a sample implementation.  Trenton Small gave me some 
     advice about how small to make classes and methods that I am too 
     bad at coding to follow.    

  KNOWN BUGS:
  
  	 I have been having trouble with text file encoding. If the input text
  	 file is saved in standard UTF-8 or western there is no problem. If it 
  	 is saved in UTF-16 then it will see characters that are not correct. 
  	 If you change the file type that the input reader accepts to UTF-16 the
  	 opposite will happen.  It will work for UTF-16 but not UTF-8 among others.  

     