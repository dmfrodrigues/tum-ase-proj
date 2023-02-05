import '../css/page/orderList.css'
import { DataGrid } from '@mui/x-data-grid';
import { orderRows } from "../dummyData";
import { useEffect, useState } from "react";
import OrderIcon from '../components/OrderIcon';
import EditOrder from '../components/EditOrder';
import DeleteModal from '../components/DeleteModal';
import NewOrder from '../components/NewOrder';
import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux'
import { OrderStatus } from './Order';
import { getOrders } from '../actions/orders';
import { getCustomers, getDeliverers } from '../actions/users';
import { getBoxes } from '../actions/boxes';
import { deleteOrder } from '../actions/orders';

function OrderList() {
  const dispatch = useDispatch()

  const [data, setData] = useState(orderRows);
  const orders = useSelector(state => state.orders.orders)
  const auth = useSelector(state => state.auth)
  const customers = useSelector(state => state.users.customers);
  const deliverers = useSelector(state => state.users.deliverers);
  const boxes = useSelector(state => state.boxes);

  useEffect(() => {
    dispatch(getOrders())
    if (auth.user.role === "DISPATCHER") {
      dispatch(getCustomers())
      dispatch(getDeliverers())
      dispatch(getBoxes())
    }

  }, [])

  const handleDelete = (id) => {
    setData(data.filter((item) => item.id !== id));
    // TODO
  };

  const getStatusOrder = (order) => {
    if (order.events.length === 0)
      return OrderStatus.ORDERED;
    return order.events[order.events.length - 1].state;
  }

  let columns = [
    {
      field: "id",
      headerName: "ID",
      width: 100,
      flex: 1,
    },
    {
      field: "status",
      headerName: "Status",
      width: 150,
      flex: 1,
      renderCell: (params) => {
        var status = getStatusOrder(params.row);
        var statusClass = "orderList" + status;
        return (
          <Link to={"/orders/" + params.row.id} className="orderListStatus">
            <div className="orderListItem">
              <OrderIcon status={getStatusOrder(params.row)} />
            </div>
            <div className={statusClass}>
              {OrderStatus[status]}
            </div >
          </Link >
        );
      },
    },
  ];
  if (auth.user.role === "DISPATCHER") {
    columns.push(
      {
        field: "customer",
        valueGetter: (params) => {
          return params.row.customer.name;
        },
        headerName: "Customer",
        flex: 1,
      }
    );
    columns.push({
      field: "dispatcher",
      valueGetter: (params) => {
        return params.row.createdBy.name;
      },
      headerName: "Dispatcher",
      flex: 1,
    },
    );
  }
  columns.push({
    field: "deliverer",
    valueGetter: (params) => {
      return params.row.deliverer ? params.row.deliverer.name : "Not Assigned";
    },
    headerName: "Deliverer",
    flex: 1,
  }, {
    field: "boxName",
    valueGetter: (params) => {
      return params.row.box.username
    },
    headerName: "Box Name",
    flex: 1,
  }, {
    field: "boxAddress",
    valueGetter: (params) => {
      return params.row.box.address
    },
    headerName: "Box Address",
    flex: 2,
  }, {
    field: "pickupAddress",
    headerName: "Pickup Address",
    flex: 2,
  });

  if (auth.user.role === "DISPATCHER") {
    columns.push(
      {
        renderHeader: () => {
          return (
            <NewOrder customers={customers} deliverers={deliverers} boxes={boxes} />
          );
        },
        flex: 1,
        field: "action",
        sortable: false,
        filterable: false,
        renderCell: (params) => {
          const handleDelete = () => {
            dispatch(deleteOrder(params.row.id));
            window.location.reload();
          };

          return (
            <div className="orderListEdit">
              <EditOrder customers={customers} deliverers={deliverers} boxes={boxes} order={params.row} />
              <DeleteModal text="Confirm Order Deletion" handleDelete={handleDelete} />
            </div>
          );
        },
      });
  }

  return (
    <div className="orderList">
      <DataGrid
        className='orderListTable'
        rows={orders}
        disableSelectionOnClick
        columns={columns}
        pageSize={8}
      />
    </div>
  );
}

export default OrderList;