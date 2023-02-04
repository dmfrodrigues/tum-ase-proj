import { configureStore } from '@reduxjs/toolkit'
import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import thunk from "redux-thunk";
import reducers from "../reducers";

const middleware = [thunk];

const persistConfig = {
    key: 'root',
    storage,
}

export const store = configureStore({
    reducer: persistReducer(persistConfig, reducers),
    middleware: middleware,
})

export const persistor = persistStore(store);