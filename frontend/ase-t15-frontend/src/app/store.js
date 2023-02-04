import { combineReducers, applyMiddleware } from "redux";
import { configureStore } from '@reduxjs/toolkit'
import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import thunk from "redux-thunk";
import orders from "../reducers/orders";
import auth from "../reducers/auth";

const middleware = [thunk];

const persistConfig = {
    key: 'root',
    storage,
}

export const store = configureStore({
    reducer: persistReducer(persistConfig,
        combineReducers({
            orders,
            auth
        })
    ),
    middleware: middleware,
})

export const persistor = persistStore(store);