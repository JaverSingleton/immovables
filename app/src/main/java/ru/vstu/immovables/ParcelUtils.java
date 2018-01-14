package ru.vstu.immovables;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.SparseArray;

import java.util.List;
import java.util.Map;

public final class ParcelUtils {

    public static final int PARCELABLE_FALSE = 0;
    public static final int PARCELABLE_TRUE = 1;

    private static final int NULL_MAP_STUB = -1;

    /**
     * Typed wrapper for {@link android.os.Parcel#writeSparseArray}
     */
    public static void writeSparseArray(@NonNull Parcel dest, @Nullable SparseArray<?> val) {
        //noinspection unchecked
        dest.writeSparseArray((SparseArray<Object>) val);
    }

    /**
     * Typed wrapper for {@link android.os.Parcel#readSparseArray}
     */
    @Nullable
    public static <T> SparseArray<T> readSparseArray(@NonNull Parcel in, @NonNull Class clazz) {
        //noinspection unchecked
        return (SparseArray<T>) in.readSparseArray(clazz.getClassLoader());
    }


    /**
     * Write values from Map<T, E> to parcel at the
     * current dataPosition()
     */
    public static <T, E> void writeValueMap(@NonNull Parcel out, @Nullable Map<T, E> map) {
        if (map == null) {
            out.writeInt(NULL_MAP_STUB);
            return;
        }
        out.writeInt(map.size());
        for (Map.Entry<T, E> entry : map.entrySet()) {
            out.writeValue(entry.getKey());
            out.writeValue(entry.getValue());
        }
    }

    /**
     * Read and return a new Map<T, E> containing values from
     * the parcel that was written with {@link ParcelUtils#writeValueMap(Parcel, Map)} at the
     * current dataPosition().  Returns <code>null</code> if the
     * previously written map was null.
     */
    @Nullable
    public static <T, E> Map<T, E> createValueMap(@NonNull Parcel in, @NonNull Class<T> keyClass, @NonNull Class<E> valueClass) {
        int N = in.readInt();
        if (N < 0) return null;

        Map map = new ArrayMap(N);
        while (N > 0) {
            Object key = in.readValue(keyClass.getClassLoader());
            Object value = in.readValue(valueClass.getClassLoader());
            //noinspection unchecked
            map.put(key, value);
            N--;
        }
        //noinspection unchecked
        return map;
    }

    public static void writeParcelableList(@NonNull Parcel out, @Nullable List<? extends Parcelable> list) {
        ParcelsKt.writeParcelableList(out, list, 0);
    }

    @Nullable
    public static <T extends Parcelable> List<T> createParcelableList(@NonNull Parcel in, @NonNull Class clazz) {
        //noinspection unchecked
        return (List<T>) ParcelsKt.createParcelableList(in, clazz);
    }

    public static void recycle(@Nullable Parcel parcel) {
        if (parcel != null) {
            parcel.recycle();
        }
    }
}
