import { configureStore } from '@reduxjs/toolkit'
import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import thunk from "redux-thunk";
import { LOGOUT } from '../actions/types';
import appReducer from "../reducers";

const middleware = [thunk];

const persistConfig = {
    key: 'root',
    storage,
}
const rootReducer = (state, action) => {
    if (action.type === LOGOUT) {
        storage.removeItem('persist:root')
        return appReducer(undefined, action)
    }

    return appReducer(state, action)
}

export const store = configureStore({
    reducer: persistReducer(persistConfig, rootReducer),
    middleware: middleware,
})

export const persistor = persistStore(store);