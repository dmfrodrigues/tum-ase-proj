import { combineReducers } from "redux";
import auth from "./auth";
import orders from "./orders";
import users from "./users";
import boxes from "./boxes";

export default combineReducers({
    'auth': auth,
    'users': users,
    'orders': orders,
    'boxes': boxes,
});