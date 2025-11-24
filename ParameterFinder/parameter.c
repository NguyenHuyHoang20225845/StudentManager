#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <sys/socket.h>
#include <ctype.h>

// Hàm kiểm tra parameter có phải IPv4 hợp lệ không
int is_valid_ipv4(const char *ip) {
    struct in_addr addr;
    return inet_pton(AF_INET, ip, &addr) == 1;
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s parameter\n", argv[0]);
        return 1;
    }

    char *parameter = argv[1];

    // Nếu parameter là IPv4
    if (is_valid_ipv4(parameter)) {
        struct in_addr addr;
        inet_pton(AF_INET, parameter, &addr);

        struct hostent *he = gethostbyaddr(&addr, sizeof(addr), AF_INET);
        if (he == NULL) {
            printf("Not found information\n");
            return 0;
        }

        printf("Result:");
        for (char **p = he->h_aliases; *p != NULL; p++) {
            printf(" %s", *p);
        }
        // Tên chính cũng cần in ra
        if (he->h_name) {
            printf(" %s", he->h_name);
        }
        printf("\n");

    } else { // Ngược lại: parameter là tên miền
        struct hostent *he = gethostbyname(parameter);
        if (he == NULL) {
            printf("Not found information\n");
            return 0;
        }

        printf("Result:");
        struct in_addr **addr_list = (struct in_addr **)he->h_addr_list;
        for (int i = 0; addr_list[i] != NULL; i++) {
            printf(" %s", inet_ntoa(*addr_list[i]));
        }
        printf("\n");
    }

    return 0;
}
