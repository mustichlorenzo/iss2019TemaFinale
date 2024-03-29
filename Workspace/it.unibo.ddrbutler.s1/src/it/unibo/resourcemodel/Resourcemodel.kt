/* Generated by AN DISI Unibo */ 
package it.unibo.resourcemodel

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Resourcemodel ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
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
					 transition(edgeName="t06",targetState="handleChange",cond=whenDispatch("robotChange"))
					transition(edgeName="t07",targetState="handleChange",cond=whenDispatch("sonarChange"))
				}	 
				state("handleChange") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("robotChange(TARGET,VALUE)"), Term.createTerm("robotChange(robot,X)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								itunibo.robot.resourceModelSupport.updateRobotModel(myself ,payloadArg(1) )
						}
						if( checkMsgContent( Term.createTerm("sonarChange(TARGET,VALUE,OBSTACLE)"), Term.createTerm("sonarChange(sonar,VALUE,OBSTACLE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("$name in ${currentState.stateName} | $currentMsg")
								var obstacle=payloadArg(2).equals("obstacle")
								itunibo.robot.resourceModelSupport.updateSonarRobotModel(myself ,payloadArg(1), obstacle )
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
