/* Generated by AN DISI Unibo */ 
package it.unibo.resourcemodelfridge

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Resourcemodelfridge ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[RESOURCEMODEL FRIDGE]: Started...")
						solve("consult('sysRules.pl')","") //set resVar	
						solve("consult('fridgeModel.pl')","") //set resVar	
						itunibo.coap.serverCoap.create(myself)
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
					}
					 transition(edgeName="t01",targetState="handleChange",cond=whenDispatch("modelChangeFridge"))
					transition(edgeName="t02",targetState="handleUpdate",cond=whenDispatch("modelUpdateFridge"))
				}	 
				state("handleChange") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("modelChangeFridge(NAME,TASK,FOODCODE,QNT)"), Term.createTerm("modelChangeFridge(fridge,OP,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								solve("action(fridge,${payloadArg(1)},${payloadArg(2)},${payloadArg(3)})","") //set resVar	
								if(currentSolution.isSuccess()) { forward("modelChangedFridge", "modelChangedFridge(${payloadArg(0)},${payloadArg(1)},${payloadArg(2)},${payloadArg(3)})" ,"fridge" ) 
								 }
								else
								{ println("Cambiamento non permesso!")
								 }
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("handleUpdate") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("modelUpdateFridge(NAME,TASK,FOODCODE,QNT)"), Term.createTerm("modelUpdateFridge(fridge,TASK,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								itunibo.fridge.resourceModelSupport.updateFridgeModel(myself ,payloadArg(1), payloadArg(2), payloadArg(3) )
						}
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}