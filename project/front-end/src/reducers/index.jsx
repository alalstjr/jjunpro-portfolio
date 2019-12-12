import { combineReducers } from "redux"
import errorsReducer from "./errorsReducer"
import accountReducer from "./accountReducer"
import pugjjigReducer from "./pugjjigReducer"

import { reducer as localeReducer } from "../connectedIntlProvider/reducer";

export default combineReducers({
    errors: errorsReducer,
    locale: localeReducer,
    account: accountReducer,
    pugjjig: pugjjigReducer
});