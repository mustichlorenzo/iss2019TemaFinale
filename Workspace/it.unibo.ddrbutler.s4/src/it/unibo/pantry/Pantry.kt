/* Generated by AN DISI Unibo */ 
package it.unibo.pantry

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Pantry ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var goToPut = false
				var qnt = -1
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[PANTRY]: Started...")
						itunibo.pantry.pantrySupport.create( 20  )
						forward("setLocation", "setLocation(pantry,0,3)" ,"planner" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						forward("modelUpdatePantry", "modelUpdatePantry(pantry,idle,null)" ,"resourcemodelpantry" ) 
					}
					 transition(edgeName="t098",targetState="analyzeMessage",cond=whenDispatch("modelChangedPantry"))
				}	 
				state("analyzeMessage") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("modelChangedPantry(NAME,OP,QNT)"), Term.createTerm("modelChangedPantry(NAME,OP,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(payloadArg(1).equals("put")){
												goToPut=true
											}
								if(payloadArg(1).equals("take")){
												goToPut=false
											}
								qnt = Integer.parseInt(payloadArg(2))
						}
					}
					 transition( edgeName="goto",targetState="putDish", cond=doswitchGuarded({goToPut}) )
					transition( edgeName="goto",targetState="takeDish", cond=doswitchGuarded({! goToPut}) )
				}	 
				state("putDish") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						itunibo.pantry.pantrySupport.putDishes(myself ,qnt )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("takeDish") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						itunibo.pantry.pantrySupport.takeDishes(myself ,qnt )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
