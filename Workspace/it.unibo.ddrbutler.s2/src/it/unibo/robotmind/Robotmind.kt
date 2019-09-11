/* Generated by AN DISI Unibo */ 
package it.unibo.robotmind

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Robotmind ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		var input=""
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[ROBOT MIND]: Started...")
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("obstacle"), Term.createTerm("obstacle"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								forward("moveFailed", "moveFailed" ,"calibration" ) 
						}
					}
					 transition(edgeName="t03",targetState="handleCmd",cond=whenDispatch("mindCmd"))
				}	 
				state("handleCmd") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("mindCmd(CMD)"), Term.createTerm("mindCmd(X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								input=payloadArg(0)
								forward("moveReceived", "moveReceived" ,"robotmind" ) 
						}
					}
					 transition(edgeName="to4",targetState="startForward",cond=whenDispatchGuarded("moveReceived",{input.equals("w")}))
					transition(edgeName="to5",targetState="startBacktracking",cond=whenDispatchGuarded("moveReceived",{input.equals("s")}))
					transition(edgeName="to6",targetState="startTurnLeft",cond=whenDispatchGuarded("moveReceived",{input.equals("a")}))
					transition(edgeName="to7",targetState="startTurnRight",cond=whenDispatchGuarded("moveReceived",{input.equals("d")}))
				}	 
				state("startForward") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("robotChange", "robotChange(robot,w)" ,"resourcemodel" ) 
						forward("robotCmd", "robotCmd(w)" ,"basicrobot" ) 
						stateTimer = TimerActor("timer_startForward", 
							scope, context!!, "local_tout_robotmind_startForward", 1000.toLong() )
					}
					 transition(edgeName="t08",targetState="stopForward",cond=whenTimeout("local_tout_robotmind_startForward"))   
					transition(edgeName="t09",targetState="waitCmd",cond=whenEvent("obstacle"))
				}	 
				state("stopForward") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						forward("robotChange", "robotChange(robot,h)" ,"resourcemodel" ) 
						forward("robotCmd", "robotCmd(h)" ,"basicrobot" ) 
						forward("moveCompleted", "moveCompleted" ,"calibration" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("startTurnLeft") { //this:State
					action { //it:State
						forward("robotChange", "robotChange(robot,a)" ,"resourcemodel" ) 
						forward("robotCmd", "robotCmd(a)" ,"basicrobot" ) 
						stateTimer = TimerActor("timer_startTurnLeft", 
							scope, context!!, "local_tout_robotmind_startTurnLeft", 1750.toLong() )
					}
					 transition(edgeName="t010",targetState="stopTurnLeft",cond=whenTimeout("local_tout_robotmind_startTurnLeft"))   
				}	 
				state("stopTurnLeft") { //this:State
					action { //it:State
						forward("robotChange", "robotChange(robot,h)" ,"resourcemodel" ) 
						forward("robotCmd", "robotCmd(h)" ,"basicrobot" ) 
						forward("moveCompleted", "moveCompleted" ,"calibration" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("startTurnRight") { //this:State
					action { //it:State
						forward("robotChange", "robotChange(robot,d)" ,"resourcemodel" ) 
						forward("robotCmd", "robotCmd(d)" ,"basicrobot" ) 
						stateTimer = TimerActor("timer_startTurnRight", 
							scope, context!!, "local_tout_robotmind_startTurnRight", 1750.toLong() )
					}
					 transition(edgeName="t011",targetState="stopTurnRight",cond=whenTimeout("local_tout_robotmind_startTurnRight"))   
				}	 
				state("stopTurnRight") { //this:State
					action { //it:State
						forward("robotChange", "robotChange(robot,h)" ,"resourcemodel" ) 
						forward("robotCmd", "robotCmd(h)" ,"basicrobot" ) 
						forward("moveCompleted", "moveCompleted" ,"calibration" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("startBacktracking") { //this:State
					action { //it:State
						forward("robotChange", "robotChange(robot,s)" ,"resourcemodel" ) 
						forward("robotCmd", "robotCmd(s)" ,"basicrobot" ) 
						stateTimer = TimerActor("timer_startBacktracking", 
							scope, context!!, "local_tout_robotmind_startBacktracking", 500.toLong() )
					}
					 transition(edgeName="t012",targetState="stopBacktracking",cond=whenTimeout("local_tout_robotmind_startBacktracking"))   
				}	 
				state("stopBacktracking") { //this:State
					action { //it:State
						forward("robotChange", "robotChange(robot,h)" ,"resourcemodel" ) 
						forward("robotCmd", "robotCmd(h)" ,"basicrobot" ) 
						forward("moveCompleted", "moveCompleted" ,"calibration" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
