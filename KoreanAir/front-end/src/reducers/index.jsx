import { combineReducers } from "redux"
import errorsReducer from "./errorsReducer"
import projectTaskReducer from "./projectTaskReducer";

import { reducer as localeReducer } from "../connectedIntlProvider/reducer";

export default combineReducers({
    errors: errorsReducer,
    project_task: projectTaskReducer,
    locale: localeReducer
});