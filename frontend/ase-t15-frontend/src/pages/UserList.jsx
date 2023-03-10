
import '../css/page/userList.css'
import { DataGrid } from '@mui/x-data-grid';
import { orderRows } from "../dummyData";
import { dispatcherRows, customerRows, boxRows } from "../dummyData";
import { useState, useEffect } from "react";
import EditOrder from '../components/EditOrder';
import DeleteModal from '../components/DeleteModal';
import { Person } from '@mui/icons-material';
import NewUser from '../components/NewUser';
import EditUser from '../components/EditUser';
import { getUsers, deleteUser, getTokens } from '../actions/users';
import { useSelector, useDispatch } from 'react-redux'

function UserList() {
  const [data, setData] = useState(customerRows);
  const dispatch = useDispatch();
  const users = useSelector(state => state.users.users);
  const tokens = useSelector(state => state.users.tokens);

  useEffect(() => {
    dispatch(getUsers());
    dispatch(getTokens());
  }, [])

  const columns = [
    {
      field: "id",
      width: 150,
      flex: 1,
      headerName: "ID",
    },
    {
      field: "name",
      width: 150,
      flex: 1,
      headerName: "Name",
      renderCell: (params) => {
        return (
          <div className="userListItem">
            <Person />
            {"  " + params.row.name}
          </div>
        );
      }
    },
    {
      field: "email",
      width: 150,
      flex: 1,
      headerName: "Email",
    },
    {
      field: "role",
      width: 150,
      flex: 1,
      headerName: "Role",
      renderCell: (params) => {
        return (
          <span className="userType">
            {params.row.role[0] + params.row.role.slice(1).toLowerCase()}
          </span>
        );
      }
    },
    {
      renderHeader: () => {
        return (
          <NewUser allTokens={tokens} />
        );
      },
      flex: 1,
      sortable: false,
      filterable: false,
      field: "action",
      renderCell: (params) => {
        const handleDelete = () => {
          dispatch(deleteUser(params.row.id));
          window.location.reload();
        };

        return (
          <div className="userListEdit">
            <EditUser user={params.row} />
            <DeleteModal text="Confirm User Deletion" handleDelete={handleDelete} />
          </div>
        );
      },
    },
  ];

  return (
    <div className="userList">
      <DataGrid
        className='userListTable'
        rows={users}
        disableSelectionOnClick
        columns={columns}
        pageSize={20}
      />
    </div>
  );
}

export default UserList;