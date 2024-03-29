%%STATE OF THE ROBOT FOR TESTING PURPOSE

state( robot, state(stopped) ).   %% initial state
state( sonar, unknown, unknown ).   %% initial state

action(robot, move(w)) :- changeModelRobot( robot, movingForward  ).
action(robot, move(s)) :- changeModelRobot( robot, movingBackward ).
action(robot, move(a)) :- changeModelRobot( robot, rotateLeft     ).
action(robot, move(d)) :- changeModelRobot( robot, rotateRight    ).
action(robot, move(h)) :- changeModelRobot( robot, stopped        ).

action(sonar, V, OBSTACLE)  :- changeModelSonar( sonar, V, OBSTACLE  ).
 

changeModelRobot( NAME, VALUE ) :-
   replaceRule( state(NAME,_),  state(NAME,state(VALUE)) ).
   %%showResourceModel.	%% at each change, show the model
   
 changeModelSonar( NAME, VALUE, OBSTACLE ) :-
   replaceRule( state(NAME,_,_),  state(NAME, VALUE, OBSTACLE) ).
   %%showResourceModel.	%% at each change, show the model