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
						println("[MAITRE]: starts...")
						itunibo.maitre.maitreGUI.init(myself)
					}
					 transition( edgeName="goto",targetState="sendingPrepare", cond=doswitch() )
				}	 
				state("sendingPrepare") { //this:State
					action { //it:State
						println("[MAITRE]: I'm in sendingPrepare")
						itunibo.maitre.maitreGUI.enableOnlyPrepare(  )
					}
					 transition(edgeName="t00",targetState="waitingPrepCompleted",cond=whenDispatch("prepareSended"))
					transition(edgeName="t01",targetState="updateSP",cond=whenDispatch("updateMaitre"))
				}	 
				state("waitingPrepCompleted") { //this:State
					action { //it:State
						itunibo.maitre.maitreGUI.enableOnlySR(  )
						forward("modelUpdate", "modelUpdate(maitre,prepare)" ,"maitremodel" ) 
						forward("taskChange", "taskChange(butler,prepare)" ,"resourcemodel" ) 
						println("[MAITRE]: waiting for a prepareCompleted...")
					}
					 transition(edgeName="t02",targetState="sendingAC",cond=whenDispatch("modelChanged"))
					transition(edgeName="t03",targetState="updateP",cond=whenDispatch("updateMaitre"))
				}	 
				state("sendingAC") { //this:State
					action { //it:State
						println("[MAITRE]: I'm in sendingAC")
						itunibo.maitre.maitreGUI.enableOnlyAC(  )
					}
					 transition(edgeName="t04",targetState="waitingAddFoodCompleted",cond=whenDispatch("addFoodSended"))
					transition(edgeName="t05",targetState="waitingClearCompleted",cond=whenDispatch("clearSended"))
					transition(edgeName="t06",targetState="updateP",cond=whenDispatch("updateMaitre"))
				}	 
				state("waitingAddFoodCompleted") { //this:State
					action { //it:State
						itunibo.maitre.maitreGUI.enableOnlySR(  )
						forward("modelUpdate", "modelUpdate(maitre,addFood)" ,"maitremodel" ) 
						forward("taskChange", "taskChange(butler,add)" ,"resourcemodel" ) 
						println("[MAITRE]: waiting for an addFoodCompleted...")
					}
					 transition(edgeName="t07",targetState="sendingAC",cond=whenDispatch("modelChanged"))
					transition(edgeName="t08",targetState="updateAC",cond=whenDispatch("updateMaitre"))
					transition(edgeName="t09",targetState="handleWarning",cond=whenEvent("alert"))
				}	 
				state("waitingClearCompleted") { //this:State
					action { //it:State
						itunibo.maitre.maitreGUI.enableOnlySR(  )
						forward("modelUpdate", "modelUpdate(maitre,clear)" ,"maitremodel" ) 
						forward("taskChange", "taskChange(butler,clear)" ,"resourcemodel" ) 
						println("[MAITRE]: waiting for a clearCompleted...")
					}
					 transition(edgeName="t010",targetState="sendingPrepare",cond=whenDispatch("modelChanged"))
					transition(edgeName="t011",targetState="updateC",cond=whenDispatch("updateMaitre"))
				}	 
				state("updateSP") { //this:State
					action { //it:State
						println("[MAITRE]: I'm in updateSP")
						itunibo.maitre.maitreGUI.readFromFile(  )
					}
					 transition( edgeName="goto",targetState="sendingPrepare", cond=doswitch() )
				}	 
				state("updateAC") { //this:State
					action { //it:State
						println("[MAITRE]: I'm in updateAC")
						itunibo.maitre.maitreGUI.readFromFile(  )
					}
					 transition( edgeName="goto",targetState="sendingAC", cond=doswitch() )
				}	 
				state("updateP") { //this:State
					action { //it:State
						println("[MAITRE]: I'm in updateP")
						itunibo.maitre.maitreGUI.readFromFile(  )
					}
					 transition( edgeName="goto",targetState="waitingPrepCompleted", cond=doswitch() )
				}	 
				state("updateA") { //this:State
					action { //it:State
						println("[MAITRE]: I'm in updateA")
					}
					 transition( edgeName="goto",targetState="waitingAddFoodCompleted", cond=doswitch() )
				}	 
				state("updateC") { //this:State
					action { //it:State
						println("[MAITRE]: I'm in updateC")
					}
					 transition( edgeName="goto",targetState="waitingClearCompleted", cond=doswitch() )
				}	 
				state("handleWarning") { //this:State
					action { //it:State
						println("[MAITRE]: received an alert")
					}
					 transition( edgeName="goto",targetState="sendingAC", cond=doswitch() )
				}	 
			}
		}
}