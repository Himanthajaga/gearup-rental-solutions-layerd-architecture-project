package lk.ijse.rental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingMaterial implements List<SellMaterial> {
    private String bm_id;
    private String bm_desc;
    private String bm_type;
    private String bm_price;
    private int bm_qty;
    private String s_email;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<SellMaterial> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(SellMaterial sellMaterial) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends SellMaterial> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends SellMaterial> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public SellMaterial get(int index) {
        return null;
    }

    @Override
    public SellMaterial set(int index, SellMaterial element) {
        return null;
    }

    @Override
    public void add(int index, SellMaterial element) {

    }

    @Override
    public SellMaterial remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<SellMaterial> listIterator() {
        return null;
    }

    @Override
    public ListIterator<SellMaterial> listIterator(int index) {
        return null;
    }

    @Override
    public List<SellMaterial> subList(int fromIndex, int toIndex) {
        return null;
    }
}
