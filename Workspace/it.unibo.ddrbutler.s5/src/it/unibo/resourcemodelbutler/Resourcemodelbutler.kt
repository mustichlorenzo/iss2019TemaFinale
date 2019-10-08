/* Generated by AN DISI Unibo */ 
package it.unibo.resourcemodelbutler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Resourcemodelbutler ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[RESOURCE MODEL]: Started...")
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('robotState.pl')","") //set resVar	
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t03",targetState="handleChange",cond=whenDispatch("robotChange"))
					transition(edgeName="t04",targetState="handleChange",cond=whenDispatch("sonarChange"))
					transition(edgeName="t05",targetState="handleChange",cond=whenDispatch("taskChange"))
					transition(edgeName="t06",targetState="handleUpdate",cond=whenDispatch("robotUpdate"))
					transition(edgeName="t07",targetState="handleUpdate",cond=whenDispatch("sonarUpdate"))
					transition(edgeName="t08",targetState="handleUpdate",cond=whenDispatch("taskUpdate"))
					transition(edgeName="t09",targetState="handleUpdate",cond=whenDispatch("positionUpdate"))
				}	 
				state("handleChange") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("robotChange(TARGET,VALUE)"), Term.createTerm("robotChange(robot,X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								solve("action(robot,move(${payloadArg(1)}))","") //set resVar	
								if(currentSolution.isSuccess()) { emit("robotChanged", "robotChanged(robot,${payloadArg(1)})" ) 
								 }
								else
								{ println("Cambiamento non accettato")
								 }
						}
						if( checkMsgContent( Term.createTerm("sonarChange(TARGET,VALUE,OBSTACLE)"), Term.createTerm("sonarChange(sonar,VALUE,OBSTACLE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
						}
						if( checkMsgContent( Term.createTerm("taskChange(TARGET,TASK,FC,QNT)"), Term.createTerm("taskChange(butler,TASK,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								solve("action(butler,${payloadArg(1)})","") //set resVar	
								if(currentSolution.isSuccess()) { emit("taskChanged", "taskChanged(butler,${payloadArg(1)},${payloadArg(2)},${payloadArg(3)})" ) 
								 }
								else
								{ println("Cambiamento non accettato")
								 }
								solve("state(butler,X,Y)","") //set resVar	
								
												var a1=getCurSol("X")
												var a2=getCurSol("Y")
								println("STATE ROBOT: butler $a1 $a2")
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("handleUpdate") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("robotUpdate(TARGET,VALUE)"), Term.createTerm("robotUpdate(robot,X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
						}
						if( checkMsgContent( Term.createTerm("sonarUpdate(TARGET,VALUE,OBSTACLE)"), Term.createTerm("sonarUpdate(sonar,VALUE,OBSTACLE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								var obstacle=payloadArg(2).equals("obstacle")
								itunibo.robot.resourceModelSupport.updateSonarRobotModel(myself ,payloadArg(1), obstacle )
						}
						if( checkMsgContent( Term.createTerm("taskUpdate(TARGET,TASK,FC,QNT)"), Term.createTerm("taskUpdate(butler,TASK,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								itunibo.robot.resourceModelSupport.updateButlerModel(myself ,payloadArg(1) )
						}
						if( checkMsgContent( Term.createTerm("positionUpdate(TARGET,X,Y)"), Term.createTerm("positionUpdate(robot,X,Y)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								itunibo.robot.resourceModelSupport.updatePositionRobotModel(myself ,payloadArg(1), payloadArg(2) )
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}