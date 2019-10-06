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
		
				var qnt = -1
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[DISHWASHER]: Started...")
						forward("setLocation","setLocation(dishwasher,3,3)","planner")
						itunibo.dishwasher.dishwasherSupport.create(  )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						forward("modelUpdateDishwasher", "modelUpdateDishwasher(dishwasher,idle,null)" ,"resourcemodeldishwasher" ) 
					}
					 transition(edgeName="t095",targetState="putDish",cond=whenDispatch("putDishDishwasher"))
					transition(edgeName="t096",targetState="takeDish",cond=whenDispatch("takeDishDishwasher"))
				}	 
				state("putDish") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("putDishDishwasher(QNT)"), Term.createTerm("putDishDishwasher(QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("modelUpdateDishwasher", "modelUpdateDishwasher(dishwasher,put,${payloadArg(0)})" ,"resourcemodeldishwasher" ) 
								qnt = Integer.parseInt(payloadArg(0))
								itunibo.dishwasher.dishwasherSupport.putDishes(myself ,qnt )
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("takeDish") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("takeDishDishwasher(QNT)"), Term.createTerm("takeDishDishwasher(QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("modelUpdateDishwasher", "modelUpdateDishwasher(dishwasher,take,${payloadArg(0)})" ,"resourcemodeldishwasher" ) 
								qnt = Integer.parseInt(payloadArg(0))
								itunibo.dishwasher.dishwasherSupport.takeDishes(myself ,qnt )
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
