/* Generated by AN DISI Unibo */ 
package it.unibo.movementhandler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Movementhandler ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var input=""
				var startF = 0L
				var endF = 0L
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[MOVEMENT HANDLER]: Started...")
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("obstacle"), Term.createTerm("obstacle"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								endF = System.currentTimeMillis()
								replyToCaller("moveFailed","moveFailed")
						}
					}
					 transition(edgeName="t09",targetState="handleCmd",cond=whenDispatch("movementCmd"))
				}	 
				state("handleCmd") { //this:State
					action { //it:State
						storeCurrentMessageForReply()
						if( checkMsgContent( Term.createTerm("movementCmd(CMD)"), Term.createTerm("movementCmd(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								input=payloadArg(0)
								forward("moveReceived", "moveReceived" ,"movementhandler" ) 
						}
					}
					 transition(edgeName="to10",targetState="startForward",cond=whenDispatchGuarded("moveReceived",{input.equals("w")}))
					transition(edgeName="to11",targetState="startBacktracking",cond=whenDispatchGuarded("moveReceived",{input.equals("s")}))
					transition(edgeName="to12",targetState="startTurnLeft",cond=whenDispatchGuarded("moveReceived",{input.equals("a")}))
					transition(edgeName="to13",targetState="startTurnRight",cond=whenDispatchGuarded("moveReceived",{input.equals("d")}))
				}	 
				state("startForward") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("robotChange", "robotChange(robot,w)" ,"resourcemodelbutler" ) 
						startF = System.currentTimeMillis()
						stateTimer = TimerActor("timer_startForward", 
							scope, context!!, "local_tout_movementhandler_startForward", 1000.toLong() )
					}
					 transition(edgeName="t014",targetState="stopForward",cond=whenTimeout("local_tout_movementhandler_startForward"))   
					transition(edgeName="t015",targetState="waitCmd",cond=whenEvent("obstacle"))
				}	 
				state("stopForward") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("robotChange", "robotChange(robot,h)" ,"resourcemodelbutler" ) 
						replyToCaller("moveCompleted","moveCompleted")
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("startTurnLeft") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("robotChange", "robotChange(robot,a)" ,"resourcemodelbutler" ) 
						stateTimer = TimerActor("timer_startTurnLeft", 
							scope, context!!, "local_tout_movementhandler_startTurnLeft", 1850.toLong() )
					}
					 transition(edgeName="t016",targetState="stopTurnLeft",cond=whenTimeout("local_tout_movementhandler_startTurnLeft"))   
				}	 
				state("stopTurnLeft") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("robotChange", "robotChange(robot,h)" ,"resourcemodelbutler" ) 
						replyToCaller("moveCompleted","moveCompleted")
						delay(1000) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("startTurnRight") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("robotChange", "robotChange(robot,d)" ,"resourcemodelbutler" ) 
						stateTimer = TimerActor("timer_startTurnRight", 
							scope, context!!, "local_tout_movementhandler_startTurnRight", 1850.toLong() )
					}
					 transition(edgeName="t017",targetState="stopTurnRight",cond=whenTimeout("local_tout_movementhandler_startTurnRight"))   
				}	 
				state("stopTurnRight") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("robotChange", "robotChange(robot,h)" ,"resourcemodelbutler" ) 
						replyToCaller("moveCompleted","moveCompleted")
						delay(1000) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("startBacktracking") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("robotChange", "robotChange(robot,s)" ,"resourcemodelbutler" ) 
					}
					 transition( edgeName="goto",targetState="waitCustomTime", cond=doswitch() )
				}	 
				state("waitCustomTime") { //this:State
					action { //it:State
						var timeToWait=(endF-startF)
						
									if(timeToWait<100)
										timeToWait=0
						println("###BACK PER $timeToWait millis")
						itunibo.planner.moveUtils.wait( timeToWait  )
					}
					 transition( edgeName="goto",targetState="stopBacktracking", cond=doswitch() )
				}	 
				state("stopBacktracking") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("robotChange", "robotChange(robot,h)" ,"resourcemodelbutler" ) 
						replyToCaller("moveCompleted","moveCompleted")
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
