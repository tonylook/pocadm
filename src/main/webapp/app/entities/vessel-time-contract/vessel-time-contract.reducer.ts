import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IVesselTimeContract, defaultValue } from 'app/shared/model/vessel-time-contract.model';

const initialState: EntityState<IVesselTimeContract> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/vessel-time-contracts';

// Actions

export const getEntities = createAsyncThunk('vesselTimeContract/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?cacheBuster=${new Date().getTime()}`;
  return axios.get<IVesselTimeContract[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'vesselTimeContract/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IVesselTimeContract>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'vesselTimeContract/create_entity',
  async (entity: IVesselTimeContract, thunkAPI) => {
    const result = await axios.post<IVesselTimeContract>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'vesselTimeContract/update_entity',
  async (entity: IVesselTimeContract, thunkAPI) => {
    const result = await axios.put<IVesselTimeContract>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'vesselTimeContract/partial_update_entity',
  async (entity: IVesselTimeContract, thunkAPI) => {
    const result = await axios.patch<IVesselTimeContract>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'vesselTimeContract/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IVesselTimeContract>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const VesselTimeContractSlice = createEntitySlice({
  name: 'vesselTimeContract',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = VesselTimeContractSlice.actions;

// Reducer
export default VesselTimeContractSlice.reducer;
