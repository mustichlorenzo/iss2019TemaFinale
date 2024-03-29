/* Generated by AN DISI Unibo */ 
package it.unibo.butlermind

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Butlermind ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var isCorrect = false
				var task : String = ""
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[BUTLERMIND]: Started...")
						delay(5000) 
					}
					 transition( edgeName="goto",targetState="calibration", cond=doswitch() )
				}	 
				state("calibration") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("taskUpdate", "taskUpdate(butler,calibration,null,null)" ,"resourcemodelbutler" ) 
						forward("startCalibration", "startCalibration" ,"calibration" ) 
					}
					 transition(edgeName="t021",targetState="waitingPrepare",cond=whenDispatch("calibrationCompleted"))
				}	 
				state("waitingPrepare") { //this:State
					action { //it:State
						println("[BUTLER_MIND]: waiting for a prepare command...")
					}
					 transition(edgeName="t022",targetState="startPrepare",cond=whenDispatch("taskChanged"))
				}	 
				state("startPrepare") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						isCorrect = false
						if( checkMsgContent( Term.createTerm("taskChanged(TARGET,TASK,FC,QNT)"), Term.createTerm("taskChanged(butler,prepare,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("startPrepare", "startPrepare" ,"preparehandler" ) 
								isCorrect = true
						}
					}
					 transition( edgeName="goto",targetState="waitingPrepare", cond=doswitchGuarded({!isCorrect}) )
					transition( edgeName="goto",targetState="waitingPrepCompleted", cond=doswitchGuarded({! !isCorrect}) )
				}	 
				state("waitingPrepCompleted") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("taskChanged(TARGET,TASK,FC,QNT)"), Term.createTerm("taskChanged(butler,stop,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("stopTask", "stopTask" ,"butlermind" ) 
								forward("stopTask", "stopTask" ,"planner" ) 
						}
					}
					 transition(edgeName="t023",targetState="notifyPrepareMaitre",cond=whenDispatch("prepareCompleted"))
					transition(edgeName="t024",targetState="waitingPrepCompleted",cond=whenDispatch("taskChanged"))
					transition(edgeName="t025",targetState="suspendedPrepare",cond=whenDispatch("stopTask"))
				}	 
				state("suspendedPrepare") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("taskChanged(TARGET,TASK,FC,QNT)"), Term.createTerm("taskChanged(butler,reactivate,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("reactivateTask", "reactivateTask" ,"butlermind" ) 
								forward("reactivateTask", "reactivateTask" ,"planner" ) 
						}
					}
					 transition(edgeName="t026",targetState="waitingPrepCompleted",cond=whenEvent("reactivateTask"))
					transition(edgeName="t027",targetState="suspendedPrepare",cond=whenDispatch("taskChanged"))
				}	 
				state("notifyPrepareMaitre") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("notifyPrepare","notifyPrepare","maitre")
					}
					 transition( edgeName="goto",targetState="waitingAC", cond=doswitch() )
				}	 
				state("waitingAC") { //this:State
					action { //it:State
						println("[BUTLER_MIND]: waiting for an AC command...")
					}
					 transition(edgeName="t028",targetState="startAC",cond=whenDispatch("taskChanged"))
				}	 
				state("startAC") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						task = ""
						if( checkMsgContent( Term.createTerm("taskChanged(TARGET,TASK,FC,QNT)"), Term.createTerm("taskChanged(TARGET,add_food,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("startAddFood", "startAddFood(${payloadArg(2)},${payloadArg(3)})" ,"addfoodhandler" ) 
								task = "add_food"
								forward("taskAssigned", "taskAssigned" ,"butlermind" ) 
						}
						if( checkMsgContent( Term.createTerm("taskChanged(TARGET,TASK,FC,QNT)"), Term.createTerm("taskChanged(TARGET,clear,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("startClear", "startClear" ,"clearhandler" ) 
								task = "clear"
						}
						forward("taskAssigned", "taskAssigned" ,"butlermind" ) 
					}
					 transition(edgeName="t029",targetState="waitingAddFoodCompleted",cond=whenDispatchGuarded("taskAssigned",{task == "add_food"}))
					transition(edgeName="t030",targetState="waitingClearCompleted",cond=whenDispatchGuarded("taskAssigned",{task == "clear"}))
					transition(edgeName="t031",targetState="waitingAC",cond=whenDispatchGuarded("taskAssigned",{task == ""}))
				}	 
				state("waitingAddFoodCompleted") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("taskChanged(TARGET,TASK,FC,QNT)"), Term.createTerm("taskChanged(butler,stop,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("stopTask", "stopTask" ,"butlermind" ) 
								forward("stopTask", "stopTask" ,"planner" ) 
						}
					}
					 transition(edgeName="t032",targetState="notifyAddFoodMaitre",cond=whenDispatch("addFoodCompleted"))
					transition(edgeName="t033",targetState="waitingAddFoodCompleted",cond=whenDispatch("taskChanged"))
					transition(edgeName="t034",targetState="suspendedAddFood",cond=whenDispatch("stopTask"))
				}	 
				state("suspendedAddFood") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("taskChanged(TARGET,TASK,FC,QNT)"), Term.createTerm("taskChanged(butler,reactivate,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("reactivateTask", "reactivateTask" ,"butlermind" ) 
								forward("reactivateTask", "reactivateTask" ,"planner" ) 
						}
					}
					 transition(edgeName="t035",targetState="waitingAddFoodCompleted",cond=whenEvent("reactivateTask"))
					transition(edgeName="t036",targetState="suspendedAddFood",cond=whenDispatch("taskChanged"))
				}	 
				state("notifyAddFoodMaitre") { //this:State
					action { //it:State
						forward("notifyAddFood","notifyAddFood","maitre")
					}
					 transition( edgeName="goto",targetState="waitingAC", cond=doswitch() )
				}	 
				state("waitingClearCompleted") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("taskChanged(TARGET,TASK,FC,QNT)"), Term.createTerm("taskChanged(butler,stop,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("stopTask", "stopTask" ,"butlermind" ) 
								forward("stopTask", "stopTask" ,"planner" ) 
						}
					}
					 transition(edgeName="t037",targetState="notifyClearMaitre",cond=whenDispatch("clearCompleted"))
					transition(edgeName="t038",targetState="waitingClearCompleted",cond=whenDispatch("taskChanged"))
					transition(edgeName="t039",targetState="suspendedClear",cond=whenDispatch("stopTask"))
				}	 
				state("suspendedClear") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("taskChanged(TARGET,TASK,FC,QNT)"), Term.createTerm("taskChanged(butler,reactivate,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("reactivateTask", "reactivateTask" ,"butlermind" ) 
								forward("reactivateTask", "reactivateTask" ,"planner" ) 
						}
					}
					 transition(edgeName="t040",targetState="waitingClearCompleted",cond=whenEvent("reactivateTask"))
					transition(edgeName="t041",targetState="suspendedClear",cond=whenDispatch("taskChanged"))
				}	 
				state("notifyClearMaitre") { //this:State
					action { //it:State
						forward("notifyClear","notifyClear","maitre")
					}
					 transition( edgeName="goto",targetState="waitingPrepare", cond=doswitch() )
				}	 
			}
		}
}
