package com.kkoch.admin.domain.plant;

import com.kkoch.admin.domain.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Category extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    @Builder
    private Category(String name, int level, boolean active, Category parent) {
        this.name = name;
        this.level =level;
        this.active = active;
        if (parent != null) {
            parent.addChild(this);
        }
    }

    /**
     * 연관관계 편의 메서드
     * 상위 카테고리 <-> 하위 카테고리 양방향 연관관계이므로
     * 현재 카테고리에 상위 클래스를 지정할 때 상위 클래스에도 현재 객체를 하위 카테고리 리스트에 추가한다.
     * (순수 객체 상태 고려)
     */
    public void changeParent(Category parent) {
        if (this.parent != null) {
            //기존에 설정된 상위 카테고리가 있다면 해당 카테고리의 하위 카테고리 리스트에서 현재 객체를 제거한다.
            this.parent.removeChild(this);
        }
        this.parent = parent;
        parent.addChild(this);
    }

    public void addChild(Category child) {
        this.children.add(child);
    }

    public void removeChild(Category child) {
        this.children.remove(child);
    }

    public void changeName(String name) {
        this.name = name;
    }
}
