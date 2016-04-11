                 CS351-F2013 Project 1 - SiteRiter

  CONTACT INFORMATION
     Project author:  Trent Small
     Email:           tsmall1@unm.edu
     Date:            Thu Sep 19 15:55:33 MST 2013
     
     
TABLE OF CONTENTS
(SEC-1). PROJECT DESCRIPTION
(SEC-2). SPECIFICATION ISSUES
(SEC-3). IMPLEMENTATION SPECIFICS
(SEC-4). ACKNOWLEDGEMENTS
(SEC-5). KNOWN BUGS

  (SEC-1). PROJECT DESCRIPTION

     SiteRiter is a custom web server working over HTML, which
     loads an SDL grammar from the drive and begins hosting
     randomized pages based on the input. Further documentation
     can be found at

     http://cs.unm.edu/~ackley/351/projects/1

     All 'extra credit' features outlined in the specification are
     present in this implementation.

  (SEC-2). SPECIFICATION ISSUES

     The following is with respect to the SiteRiter v1.0 specification.
     Several issues which I found ambiguous were:
     
     - ISSUE #1: (C.6.3.2) RELOAD AFTER PAGE GENERATION
       The spec says nothing about what happens on the thread that requests
       the "/reload" page. The default response was to send a page back that
       just says "Reloaded", but the spec does not mention this behavior.
       
       Instead, the spec says:

        ((C.6.3.2.4)) After the Rules File has been successfully reloaded,
        SiteRiter will resume generating pages in response to connections,
        including any connections that may have been temporarily blocked
        during 'advanced reload processing'.

       This thread was not temporarily blocked since it did all of the
       reloading. It does however say that it will resume generating pages
       in response to connections. Therefore, I decided that after the
       connection that reloaded the page finished reloading, SiteRiter would
       generate a page for that connection as well.

     - ISSUE #2 (C.6.3.2.1) THREAD INTERRUPT AMBIGUITY
       Blocking any threads makes java liable to throw an InterruptedException,
       but the specification mentions nothing about what happens to these threads
       if they are somehow interrupted. I am a little new to threading, and I
       am unsure if these threads _can_ even be interrupted. However, I
       wrote that these threads, if interrupted, will catch the exception and
       send back a page stating that the generating thread was interrupted.
       
     - ISSUE #3 (C.6.3.2.1) THREAD TIMEOUT
       Blocking threads may cause the incoming browser to timeout. There are
       ways to keep the connections alive, but the specification says nothing
       about this. I have decided that if a page is taking too long to
       generate that a timeout is acceptable and the user may refresh the
       page, or since this is SiteRiter, get a new page.
       
  (SEC-3). IMPLEMENTATION SPECIFICS
  
     The enclosed SiteRiter is a fully-conforming implementation of the
     SiteRiter v1.0 specification.
     
     Here, I will describe some of the internals of my design:
     
     One. PARSING PROCEDURE
          When parser.load(Reader) is called, many things happen.
          
          1. Tokenization
             The Reader is sent to the Tokenizer, which returns an array of
             Tokens.

            I. The Tokenizer uses Reader.mark(int) to function. If mark is not
               supported, the Reader is converted into one that does support
               mark.

          2. Verification
             The Token array goes into the verifier.

            I. If these Tokens are not in any way a legal SDL grammar, an
               SDLParseException is thrown. Verifying never returns false.

          3. Symbol Registration
		     The (now guaranteed valid) Token array is translated into
		     Symbols and entered into a SymbolTable.
	
	 Two. PAGE GENERATION PROCEDURE
	      When parser.makePage(String, Map) is called:
	      
	      1. Symbol Selection
	         The start symbol is determined.
	         
	            (FOR EXTRA CREDIT)
	         I. If this start symbol is exactly "/robots.txt", the start
	            symbol can only be the one named ROBOTS_TXT . If it is
	            not there, null is returned (and only returned in this case)
	            and the server will handle it.
	            
	      2. Symbol Expansion
	         These Symbols are put through SymbolExpander.expand() . The
	         details here are the same as in the specification. The final
	         expansion is sent to the web server.
	         
	         (FOR EXTRA CREDIT)
	  Three. THREAD HANDLING PROCEDURE
	         Each connection comes through the web server and is started
	         on its own thread. Each usually just uses 
	         parser.makePage(String, Map) and closes.
	         
	         1. Reload Processing
	            If a connection asks for "/reload", Threads are organized
	            using the Ballroom. Details are documented in the Ballroom
	            interface, but here is the short story.
	            
	            I. The reloading thread locks the 'door' to the ballroom.
	               It then waits for all other Threads inside to leave. Any
	               thread trying to connect after this will wait at the
	               ballroom door.
	            
	            II. The reloading thread reloads the parser with the correct
	                file via a FileReader.
	                
	            III. If this file is not valid, SDLServer will stop with a 
	                 call to System.exit(1).
	                
	            IV. The reloading thread then leaves the ballroom and unlocks
	            	the 'door', which allows it and all of the other threads
	            	left to wait outside to generate pages and exit like
	            	normal.
	                 
	  Four. STARTING THE SERVER
	        The main method is located in the SDLServer class.
	        
	        --Name--
	        
	        SDLServer - runs an SDLServer which loads an SDL language and hosts
	                    it on the web.
	                    
            --Synopsis--
            
            SDLServer FILE [PORT]
            
            --Description--
            
            Runs an SDLServer with an SDLParser loaded with the valid SDL
            file at FILE on port 8000.
            
            FILE 
                  The file path to load from. There must be a valid SDL
                  file at this location for the server to start.
                  
            PORT
                  Optionally, the server can be started on another port.
                  If this argument is not specified, port 8000 is used.
                  If this port is invalid, either in format or this port
                  is already in use, SiteRiter will exit with an error
                  message.
                  
            --Exit Codes--
            
            0 -- The server ran normally.
            1 -- The server attempted to RELOAD a bogus SDL file.
            
            A sample sdl file is located in the root of P1D3.jar, called
            'newspage.sdl'.
            
  (SEC-4). ACKNOWLEDGEMENTS
  
    Professor Ackley designed the specification for SiteRiter and provided
    some unit tests, which helped me realize that my implementation would
    have a StackOverflowError when trying to load a large grammar. I have
    since corrected this by making my Tokenizer work in a non-recursive manner.

  (SEC-5). KNOWN BUGS

    At this time, I am unable to find any bugs in this implementation.