import { combineReducers } from "redux"
import errorsReducer from "./errorsReducer"
import boardTaskReducer from "./boardTaskReducer"
import accountReducer from "./accountReducer"

import { reducer as localeReducer } from "../connectedIntlProvider/reducer";

export default combineReducers({
    errors: errorsReducer,
    locale: localeReducer,
    board_task: boardTaskReducer,
    account: accountReducer
});