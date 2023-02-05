import {
    GET_BOXES
} from "./types";

import BoxDataService from "../api/box.service";

export const getBoxes = () => async (dispatch) => {
    try {
        const res = await BoxDataService.getBoxes();

        dispatch({
            type: GET_BOXES,
            payload: res.data,
        });
    } catch (err) {
        console.log(err);
    }
}

export const createBox = (data) => async (dispatch) => {
    try {
        const res = await BoxDataService.create(data);

        dispatch(getBoxes());
    } catch (err) {
        console.log(err);
    }
}

export const editBox = (data) => async (dispatch) => {
    try {
        const res = await BoxDataService.edit(data);

        dispatch(getBoxes());
    } catch (err) {
        console.log(err);
    }
}

export const deleteBox = (id) => async (dispatch) => {
    try {
        const res = await BoxDataService.delete(id);

        dispatch(getBoxes());
    } catch (err) {
        console.log(err);
    }
}