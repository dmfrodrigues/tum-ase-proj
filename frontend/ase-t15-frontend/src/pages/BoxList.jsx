
import '../css/page/boxList.css'
import { DataGrid } from '@mui/x-data-grid';
import { orderRows } from "../dummyData";
import { dispatcherRows, customerRows, boxRows } from "../dummyData";
import { useState } from "react";
import EditOrder from '../components/EditOrder';
import DeleteModal from '../components/DeleteModal';
import EditUser from '../components/EditUser';
import { Box } from '@mui/material';
import NewBox from '../components/NewBox';
import EditBox from '../components/EditBox';
import { Inventory2Outlined, InventoryOutlined } from '@mui/icons-material';

function BoxList() {
  const [data, setData] = useState(boxRows);

  console.log(data)

  const columns = [
    {
      field: "id",
      width: 150,
      flex: 1,
      headerName: "ID",
    },
    {
      field: "location",
      width: 150,
      flex: 1,
      headerName: "Location",
      renderCell: (params) => {
        return (
          <div className="userListItem">
            <Inventory2Outlined />
            {"  " + params.row.location}
          </div>
        );
      }
    },
    {
      field: "active",
      width: 150,
      flex: 1,
      headerName: "Is Active",
      renderCell: (params) => {
        return (
          <span className="boxActive">
            {params.row.active ? "Yes" : "No"}
          </span>
        );
      }
    },
    {
      field: "status",
      width: 150,
      flex: 1,
      headerName: "Status",
      renderCell: (params) => {
        return (
          <span className="boxStatus">
            {params.row.status[0].toUpperCase() + params.row.status.slice(1)}
          </span>
        );
      }
    },
    {
      renderHeader: () => {
        return (
          <NewBox />
        );
      },
      flex: 1,
      sortable: false,
      filterable: false,
      renderCell: (params) => {
        return (
          <div className="orderListEdit">
            <EditBox box={params.row} />
            <DeleteModal text="Confirm Box Deletion" />
          </div>
        );
      },
    },
  ];

  return (
    <div className="boxList">
      <DataGrid
        className='boxListTable'
        rows={data}
        disableSelectionOnClick
        columns={columns}
        pageSize={8}
      />
    </div>
  );
}

export default BoxList;