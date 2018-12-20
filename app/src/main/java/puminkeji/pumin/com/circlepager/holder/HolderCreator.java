package puminkeji.pumin.com.circlepager.holder;

public interface HolderCreator<VH extends ViewHolder> {
    /**
     * 创建ViewHolder
     */
    VH createViewHolder();
}
