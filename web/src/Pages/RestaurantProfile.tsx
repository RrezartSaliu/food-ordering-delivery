import type { RestaurantProfile as RestaurantPofileType } from "../types/Profile";
import axios from "axios";
import { useAuth } from "../util/AuthProvider";
import type { Product } from "../types/Product";
import { useEffect, useState } from "react";
import style from "../style/RestaurantProfile.module.css";
import type { ApiResponse } from "../types/ApiResponse";

interface Prop {
  profile: RestaurantPofileType;
}

interface ItemsDisplayProps {
  items: Record<string, Product[]>;
  setItems: React.Dispatch<React.SetStateAction<Record<string, Product[]>>>;
}

const ItemsDisplay = ({ items, setItems }: ItemsDisplayProps) => {
  const [openCategory, setOpenCategory] = useState<string | null>(null);
  const [isAdding, setIsAdding] = useState(false);
  const { token } = useAuth();
  const [name, setName] = useState("");
  const [price, setPrice] = useState<number | "">("");
  const [editingProductId, setEditingProductId] = useState<number | null>(null);
  const [editName, setEditName] = useState("");
  const [editPrice, setEditPrice] = useState<number | "">("");

  const saveMethod = async () => {
    if (!token || !openCategory || !name || price == null) return;

    const res = await axios.post<ApiResponse<Product>>(
      "http://localhost:8080/restaurant/protected/create-item",
      {
        name,
        price,
        category: openCategory,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    const newProduct = res.data.data;

    setItems((prev) => ({
      ...prev,
      [openCategory]: prev[openCategory]
        ? [...prev[openCategory], newProduct]
        : [newProduct],
    }));

    setName("");
    setPrice("");
    setIsAdding(false);
  };

  const deleteItem = async (id: number) => {
    if (!token || !openCategory) return;

    const res = await axios.delete<ApiResponse<Product>>(
      `http://localhost:8080/restaurant/protected/delete-item/${id}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    const toRemoveProduct = res.data.data;

    setItems((prev) => ({
      ...prev,
      [openCategory]: prev[openCategory].filter(
        (prod) => prod.id !== toRemoveProduct.id
      ),
    }));
  };

  const saveEdit = async (id: number) => {
    if (!token || !openCategory || !editName || price == null) return;

    const res = await axios.post<ApiResponse<Product>>(
      `http://localhost:8080/restaurant/protected/update-item?itemId=${id}`,
      {
        name: editName,
        price: editPrice,
        category: openCategory,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    const updatedProduct = res.data.data;

    setItems((prev) => ({
      ...prev,
      [openCategory]: prev[openCategory].map((prod) =>
        prod.id === updatedProduct.id ? updatedProduct : prod
      ),
    }));

    setEditName("")
    setEditPrice("")
    setEditingProductId(null)
  };

  return (
    <div style={{ width: "50%" }}>
      {Object.entries(items).map(([category, products]) => (
        <div className={style.items_category_preview} key={category}>
          <h2>{category}</h2>

          {products.slice(0, 1).map((product) => (
            <p key={product.id}>
              {product.name} - {product.price} MKD
            </p>
          ))}

          {products.length > 1 && (
            <button onClick={() => setOpenCategory(category)}>Show more</button>
          )}
        </div>
      ))}

      {openCategory && (
        <div className={style.modal_overlay}>
          <div className={style.modal}>
            {!isAdding ? (
              <>
                <input
                  type="button"
                  value="Add item"
                  onClick={() => setIsAdding(true)}
                />

                <h2>{openCategory}</h2>

                {items[openCategory].map((product) =>
                  editingProductId !== product.id ? (
                    <p key={product.id}>
                      {product.name} - {product.price} MKD
                      <input
                        type="button"
                        value="delete"
                        onClick={async (e) => {
                          await deleteItem(product.id);
                        }}
                      />
                      <input
                        type="button"
                        value="edit"
                        onClick={() => {
                          setEditingProductId(product.id);
                          setEditName(product.name);
                          setEditPrice(product.price);
                        }}
                      />
                    </p>
                  ) : (
                    <p key={product.id}>
                      <input
                        type="text"
                        value={editName}
                        onChange={(e) => setEditName(e.target.value)}
                      />
                      <input
                        type="number"
                        value={editPrice}
                        onChange={(e) => setEditPrice(Number(e.target.value))}
                      />
                      <button onClick={() => saveEdit(product.id)}>Save</button>
                      <button onClick={() => setEditingProductId(null)}>
                        Cancel
                      </button>
                    </p>
                  )
                )}

                <button
                  onClick={() => {
                    setOpenCategory(null);
                    setIsAdding(false);
                  }}
                >
                  Close
                </button>
              </>
            ) : (
              <>
                <button onClick={() => setIsAdding(false)}>← Back</button>

                <h2>Add new item</h2>

                <form
                  onSubmit={async (e) => {
                    e.preventDefault();
                    await saveMethod();
                  }}
                >
                  <input
                    type="text"
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                  />

                  <input
                    type="number"
                    placeholder="Price"
                    value={price}
                    onChange={(e) => setPrice(Number(e.target.value))}
                  />

                  <button type="submit">Save</button>
                </form>
              </>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

const RestaurantProfile = ({ profile }: Prop) => {
  const { token } = useAuth();
  const [items, setItems] = useState<Record<string, Product[]>>({});

  useEffect(() => {
    if (!token) return;

    axios
      .get("http://localhost:8080/restaurant/protected/my-items-grouped", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setItems(res.data?.data);
      })
      .catch((err) => {
        console.error(err.response?.data || err.message);
      });
  }, [token]);

  return (
    <div id="container">
      <h1>Restaurant Profile</h1>
      <p>email: {profile.email}</p>
      <p>name: {profile.name}</p>
      <p>address: {profile.address}</p>
      <ItemsDisplay items={items} setItems={setItems}></ItemsDisplay>
    </div>
  );
};

export default RestaurantProfile;
