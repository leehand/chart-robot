package com.mongcent.core.commons.util;

import com.mongcent.core.commons.ui.Tree;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author zl
 * @Date 2018/11/27
 **/
public class TreeUtil {

    public static List<Tree> buildTree(List<Tree> treeDatas) {
        if (CollectionUtils.isEmpty(treeDatas)) {
            return null;
        }
        // 构建一个Map,把所有原始数据的ID作为Key,原始数据对象作为VALUE
        Map<String, Tree> nodeMap = treeDatas.stream().collect(Collectors.toMap(Tree::getId, a -> a, (k1, k2) -> k1));
        List<Tree> trees = new ArrayList();
        for (Tree tree : treeDatas) {
            if (StringUtils.isEmpty(tree.getPid())) {
                // 如果是顶层节点，直接添加到结果集合中
                trees.add(tree);
            } else {
                Tree parentTree = nodeMap.get(tree.getPid());
                // 如果不是顶层节点，找的父节点，然后添加到父节点的子节点中
                if (parentTree != null) {
                    if (parentTree.getChildren() == null) {
                        parentTree.setChildren(new ArrayList<>());
                    }
                    parentTree.getChildren().add(tree);
                }
            }
        }
        return trees;
    }
}
