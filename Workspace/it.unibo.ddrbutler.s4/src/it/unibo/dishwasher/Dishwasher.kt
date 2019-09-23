/* Generated by AN DISI Unibo */ 
package it.unibo.dishwasher

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Dishwasher ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var goToPut = false
				var qnt = -1
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[DISHWASHER]: Started...")
						forward("setLocation", "setLocation(dishwasher,3,3)" ,"planner" ) 
						itunibo.dishwasher.dishwasherSupport.create(  )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						forward("modelUpdateDishwasher", "modelUpdateDishwasher(dishwasher,idle,null)" ,"resourcemodeldishwasher" ) 
					}
					 transition(edgeName="t079",targetState="analyzeMsg",cond=whenDispatch("modelChangedDishwasher"))
				}	 
				state("analyzeMsg") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("modelChangedDishwasher(NAME,OP,QNT)"), Term.createTerm("modelChangedDishwasher(dishwasher,OP,QNT)"), 
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
						itunibo.dishwasher.dishwasherSupport.putDishes(myself ,qnt )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("takeDish") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						itunibo.dishwasher.dishwasherSupport.takeDishes(myself ,qnt )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
