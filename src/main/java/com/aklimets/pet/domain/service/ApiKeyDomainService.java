package com.aklimets.pet.domain.service;

import com.aklimets.pet.util.datetime.TimeSource;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ApiKeyDomainService {

    private TimeSource timeSource;

    public String generateApiKey() {
        var uuid = UUID.randomUUID().toString();
        var nanos = timeSource.getCurrentLocalDateTime().getNano();
        var uuid2 = UUID.randomUUID().toString();
        return Hashing.sha256()
                .hashString(uuid + nanos + uuid2, StandardCharsets.UTF_8)
                .toString();
    }
}
