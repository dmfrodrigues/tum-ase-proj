import {
    GET_BOXES,
} from "../actions/types";

const initialState = [];

function boxReducer(boxes = initialState, action) {
    const { type, payload } = action;

    switch (type) {
        case GET_BOXES:
            return [payload];

        default:
            return boxes;
    }
}

export default boxReducer;