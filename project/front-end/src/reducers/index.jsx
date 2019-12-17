import { combineReducers } from "redux";
import errorsReducer from "./errorsReducer";
import accountReducer from "./accountReducer";
import pugjjigReducer from "./pugjjigReducer";

export default combineReducers({
    errors: errorsReducer,
    account: accountReducer,
    pugjjig: pugjjigReducer
});