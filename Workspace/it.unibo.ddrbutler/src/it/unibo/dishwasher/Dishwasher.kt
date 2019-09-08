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
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[DISHWASHER]: Started...")
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						println("[DISHWASHER]: Sono in waitCmd")
					}
					 transition(edgeName="t032",targetState="puttingDish",cond=whenDispatch("dishwasherPutDish"))
					transition(edgeName="t033",targetState="takingDish",cond=whenDispatch("dishwasherTakeDish"))
				}	 
				state("puttingDish") { //this:State
					action { //it:State
						println("[DISHWASHER]: Sono in puttingDish")
						if( checkMsgContent( Term.createTerm("putDish(QNT)"), Term.createTerm("dishwasherPutDish(QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
						}
					}
					 transition(edgeName="t034",targetState="waitCmd",cond=whenDispatch("dishwasherPutCompleted"))
				}	 
				state("takingDish") { //this:State
					action { //it:State
						println("[DISHWASHER]: Sono in takingDish")
						if( checkMsgContent( Term.createTerm("takeDish(QNT)"), Term.createTerm("dishwasherTakeDish(QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
						}
					}
					 transition(edgeName="t035",targetState="waitCmd",cond=whenDispatch("dishwasherTakeCompleted"))
				}	 
			}
		}
}
