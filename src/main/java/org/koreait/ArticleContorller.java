package org.koreait;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ArticleContorller {
    private Map<Integer, Article> articles;
    private int lastId;
    private Scanner sc;

    ArticleContorller(Scanner sc) {
        this.articles = new HashMap<>();
        this.lastId = 0;
        this.sc = sc;
    }

    public Map<Integer, Article> getArticles() {
        return articles;
    }

    public void setArticles(Map<Integer, Article> articles) {
        this.articles = articles;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    public void doWrite() {
        int id = lastId + 1;
        System.out.println("게시글을 작성합니다.");
        System.out.print("title : ");
        String title = sc.nextLine();
        System.out.print("body : ");
        String body = sc.nextLine();
        System.out.printf("%d번 게시글이 작성되었습니다.\n", id);
        articles.put(id, new Article(id, title, body));
        lastId++;
    }

    public void doList() {
        if (articles.isEmpty()) System.out.println("아직 아무런 글도 없습니다.");
        else {
            System.out.println("  번호  /     제목     /     내용     ");
            for (int i = lastId; i > 0; i--) {
                if (articles.containsKey(i)) {
                    Article article = articles.get(i);
                    System.out.printf("   %d   /     %s     /     %s     \n", article.getId(), article.getTitle(), article.getBody());
                }
            }
        }
    }
}
