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
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[PANTRY]: Started...")
					}
					 transition( edgeName="goto",targetState="initialize", cond=doswitch() )
				}	 
				state("initialize") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						println("[PANTRY]: Sono in waitCmd")
					}
					 transition(edgeName="t058",targetState="puttingDish",cond=whenDispatch("pantryPutDish"))
					transition(edgeName="t059",targetState="takingDish",cond=whenDispatch("pantryTakeDish"))
				}	 
				state("puttingDish") { //this:State
					action { //it:State
						println("[PANTRY]: Sono in puttingDish")
						if( checkMsgContent( Term.createTerm("putDish(QNT)"), Term.createTerm("pantryPutDish(QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("takingDish") { //this:State
					action { //it:State
						println("[PANTRY]: Sono in takingDish")
						if( checkMsgContent( Term.createTerm("takeDish(QNT)"), Term.createTerm("pantryTakeDish(QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
