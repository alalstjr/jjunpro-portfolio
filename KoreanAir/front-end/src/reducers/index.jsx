import { combineReducers } from "redux"
import errorsReducer from "./errorsReducer"
import boardTaskReducer from "./boardTaskReducer";

import { reducer as localeReducer } from "../connectedIntlProvider/reducer";

export default combineReducers({
    errors: errorsReducer,
    board_task: boardTaskReducer,
    locale: localeReducer
});