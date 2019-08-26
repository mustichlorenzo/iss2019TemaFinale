/* Generated by AN DISI Unibo */ 
package it.unibo.maitre

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Maitre ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[MAITRE]: Started...")
					}
					 transition( edgeName="goto",targetState="sendingPrepare", cond=doswitch() )
				}	 
				state("sendingPrepare") { //this:State
					action { //it:State
						println("[MAITRE]: Sono in sendingPrepare")
						delay(4000) 
						forward("prepare", "prepare" ,"butler" ) 
						forward("prepareSended", "prepareSended" ,"maitre" ) 
					}
					 transition(edgeName="t038",targetState="waitingPrepCompletion",cond=whenDispatch("prepareSended"))
				}	 
				state("waitingPrepCompletion") { //this:State
					action { //it:State
						println("[MAITRE]: Sono in waitingPrepCompletion")
					}
					 transition(edgeName="t039",targetState="sendingAC",cond=whenDispatch("prepareCompleted"))
					transition(edgeName="t040",targetState="updateP",cond=whenEvent("updateContent"))
				}	 
				state("sendingAC") { //this:State
					action { //it:State
						println("[MAITRE]: Sono in sendingAC")
						forward("addFood", "addFood(aaaa,10)" ,"butler" ) 
						forward("addFoodSended", "addFoodSended" ,"maitre" ) 
					}
					 transition(edgeName="t041",targetState="waitingAddFoodCompletion",cond=whenDispatch("addFoodSended"))
					transition(edgeName="t042",targetState="waitingClearCompletion",cond=whenDispatch("clearSended"))
				}	 
				state("waitingAddFoodCompletion") { //this:State
					action { //it:State
						println("[MAITRE]: Sono in waitingAddFoodCompletion")
					}
					 transition(edgeName="t043",targetState="sendingAC",cond=whenDispatch("addFoodCompleted"))
					transition(edgeName="t044",targetState="updateA",cond=whenEvent("updateContent"))
				}	 
				state("waitingClearCompletion") { //this:State
					action { //it:State
						println("[MAITRE]: Sono in waitingClearCompletion")
					}
					 transition(edgeName="t045",targetState="sendingPrepare",cond=whenDispatch("clearCompleted"))
					transition(edgeName="t046",targetState="updateC",cond=whenEvent("updateContent"))
				}	 
				state("updateP") { //this:State
					action { //it:State
						println("[MAITRE]: Sono in updateP")
						if( checkMsgContent( Term.createTerm("updateContent(DEVICE,CODE,QNT)"), Term.createTerm("updateContent(DEVICE,CODE,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
						}
					}
					 transition( edgeName="goto",targetState="waitingPrepCompletion", cond=doswitch() )
				}	 
				state("updateA") { //this:State
					action { //it:State
						println("[MAITRE]: Sono in updateA")
						if( checkMsgContent( Term.createTerm("updateContent(DEVICE,CODE,QNT)"), Term.createTerm("updateContent(DEVICE,CODE,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
						}
					}
					 transition( edgeName="goto",targetState="waitingAddFoodCompletion", cond=doswitch() )
				}	 
				state("updateC") { //this:State
					action { //it:State
						println("[MAITRE]: Sono in updateC")
						if( checkMsgContent( Term.createTerm("updateContent(DEVICE,CODE,QNT)"), Term.createTerm("updateContent(DEVICE,CODE,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
						}
					}
					 transition( edgeName="goto",targetState="waitingClearCompletion", cond=doswitch() )
				}	 
			}
		}
}
