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