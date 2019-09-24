/* Generated by AN DISI Unibo */ 
package it.unibo.table

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Table ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
		
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				var goToPut = false
				var type = ""
				var num = -1
				var fc = ""
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("[TABLE]: Started...")
						itunibo.table.tableSupport.create(  )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						
						
						forward("modelUpdateTable", "modelUpdateTable(table,null,idle,null,null)" ,"resourcemodeltable" ) 
					}
					 transition(edgeName="t085",targetState="analyzeMsg",cond=whenDispatch("modelChangedTable"))
				}	 
				state("analyzeMsg") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("modelChangedTable(NAME,TYPE,TASK,FOODCODE,QNT)"), Term.createTerm("modelChangedTable(table,TYPE,TASK,FC,QNT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(payloadArg(2).equals("put"))
												goToPut=true
								if(payloadArg(2).equals("take"))
												goToPut=false
								
												num = Integer.parseInt(payloadArg(4))
												type = payloadArg(1)
												fc = payloadArg(3)
						}
					}
					 transition( edgeName="goto",targetState="putElement", cond=doswitchGuarded({goToPut }) )
					transition( edgeName="goto",targetState="takeElement", cond=doswitchGuarded({! goToPut }) )
				}	 
				state("putElement") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if(type.equals("food"))
						itunibo.table.tableSupport.putFood(myself ,fc, num )
						if(type.equals("dish"))
						itunibo.table.tableSupport.putDish(myself ,num )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("takeElement") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if(type.equals("food"))
						itunibo.table.tableSupport.takeFood(myself ,fc, num )
						if(type.equals("dish"))
						itunibo.table.tableSupport.takeDish(myself ,num )
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}