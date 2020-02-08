import {
  GET_BACKLOG,
  GET_PROJECT_TASK,
  DELETE_PROJECT_TASK
} from "../actions/types";

const initialState = {
  project_tasks: [],
  project_task: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_BACKLOG: // when we do API call, we load what we get from it
      return {
        ...state,
        project_tasks: action.payload //what we pass from action to reducer if well
      };

    case GET_PROJECT_TASK:
      return {
        ...state,
        project_task: action.payload
      };

    case DELETE_PROJECT_TASK:
      return {
        ...state,

        // to na dole odswieza kiedy usuniemy project task
        project_tasks: state.project_tasks.filter(
          project_task => project_task.projectSequence !== action.payload
        )
      };

    default:
      return state;
  }
}
